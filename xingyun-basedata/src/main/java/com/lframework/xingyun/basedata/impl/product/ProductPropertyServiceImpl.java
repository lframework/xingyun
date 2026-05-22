package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.event.DataChangeEventBuilder;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.EnumUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyModelorDto;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.xingyun.basedata.enums.ColumnDataType;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.PropertyType;
import com.lframework.xingyun.basedata.events.DeleteProductPropertyEvent;
import com.lframework.xingyun.basedata.mappers.ProductPropertyMapper;
import com.lframework.xingyun.basedata.service.product.ProductPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyService;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductPropertyVo;
import com.lframework.xingyun.basedata.vo.product.property.UpdateProductPropertyVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductPropertyServiceImpl extends
    BaseMpServiceImpl<ProductPropertyMapper, ProductProperty>
    implements ProductPropertyService {

  @Autowired
  private ProductPropertyRelationService productPropertyRelationService;

  @Override
  public PageResult<ProductProperty> query(Integer pageIndex, Integer pageSize,
      QueryProductPropertyVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductProperty> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductProperty> query(QueryProductPropertyVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = ProductProperty.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public ProductProperty findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "删除商品分类属性，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Wrapper<ProductProperty> updateWrapper = Wrappers.lambdaUpdate(ProductProperty.class)
        .set(ProductProperty::getAvailable, Boolean.FALSE).eq(ProductProperty::getId, id);
    getBaseMapper().update(updateWrapper);

    ProductProperty productProperty = this.findById(id);

    DataChangeEventBuilder.publishLogicDelete(this, DeleteProductPropertyEvent.class,
        productProperty);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "新增商品分类属性，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateProductPropertyVo vo) {

    Wrapper<ProductProperty> checkCodeWrapper = Wrappers.lambdaQuery(ProductProperty.class)
        .eq(ProductProperty::getCode, vo.getCode()).eq(ProductProperty::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductProperty> checkNameWrapper = Wrappers.lambdaQuery(ProductProperty.class)
        .eq(ProductProperty::getName, vo.getName()).eq(ProductProperty::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    ProductProperty data = new ProductProperty();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setIsRequired(vo.getIsRequired());
    data.setColumnType(EnumUtil.getByCode(ColumnType.class, vo.getColumnType()));
    if (data.getColumnType() == ColumnType.CUSTOM) {
      data.setColumnDataType(ColumnDataType.STRING);
    }
    data.setPropertyType(PropertyType.APPOINT);
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = BaseDataOpLogType.class, name = "修改商品分类属性，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateProductPropertyVo vo) {

    ProductProperty data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("属性不存在！");
    }

    Wrapper<ProductProperty> checkWrapper = Wrappers.lambdaQuery(ProductProperty.class)
        .eq(ProductProperty::getCode, vo.getCode()).eq(ProductProperty::getAvailable, Boolean.TRUE)
        .ne(ProductProperty::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductProperty> checkNameWrapper = Wrappers.lambdaQuery(ProductProperty.class)
        .eq(ProductProperty::getName, vo.getName()).eq(ProductProperty::getAvailable, Boolean.TRUE)
        .ne(ProductProperty::getId, vo.getId());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    //如果字段类型是手动录入，那么不允许修改字段类型
    if (data.getColumnType() == ColumnType.CUSTOM) {
      if (vo.getColumnType() != ColumnType.CUSTOM.getCode().intValue()) {
        throw new InputErrorException(
            "该属性的字段类型为“" + ColumnType.CUSTOM.getDesc() + "”，不允许修改！");
      }

      if (data.getColumnDataType() != ColumnDataType.STRING) {
        throw new InputErrorException("数据类型不允许修改！");
      }
    }

    if (data.getColumnType() != ColumnType.CUSTOM
        && vo.getColumnType() == ColumnType.CUSTOM.getCode().intValue()) {
      //从其他类型更改为手动录入
      throw new InputErrorException(
          "该属性不允许将字段类型修改为“" + ColumnType.CUSTOM.getDesc() + "”！");
    }

    LambdaUpdateWrapper<ProductProperty> updateWrapper = Wrappers.lambdaUpdate(
            ProductProperty.class)
        .set(ProductProperty::getCode, vo.getCode()).set(ProductProperty::getName, vo.getName())
        .set(ProductProperty::getIsRequired, vo.getIsRequired())
        .set(ProductProperty::getColumnType, vo.getColumnType())
        .set(ProductProperty::getPropertyType, PropertyType.APPOINT.getCode())
        .set(ProductProperty::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(ProductProperty::getId, vo.getId());
    if (vo.getColumnType() != ColumnType.CUSTOM.getCode().intValue()) {
      updateWrapper.set(ProductProperty::getColumnDataType, null);
    } else {
      updateWrapper.set(ProductProperty::getColumnDataType, ColumnDataType.STRING.getCode());
    }

    getBaseMapper().update(updateWrapper);

    if (data.getColumnType() == ColumnType.MULTIPLE
        && vo.getColumnType() == ColumnType.SINGLE.getCode()
        .intValue()) {
      //从多选更改为单选
      productPropertyRelationService.setMultipleToSimple(data.getId());
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public List<ProductPropertyModelorDto> getModelorByCategoryId(String categoryId) {

    List<ProductPropertyModelorDto> datas = getBaseMapper().getModelorByCategoryId(categoryId);
    return datas;
  }

  @CacheEvict(value = ProductProperty.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
