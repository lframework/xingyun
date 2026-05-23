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
import com.lframework.xingyun.basedata.dto.product.property.ProductCategoryPropertyDefinitionModelorDto;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyDefinition;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.xingyun.basedata.enums.ColumnDataType;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.PropertyType;
import com.lframework.xingyun.basedata.events.DeleteProductCategoryPropertyDefinitionEvent;
import com.lframework.xingyun.basedata.mappers.ProductCategoryPropertyDefinitionMapper;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyValueRelationService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyDefinitionService;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductCategoryPropertyDefinitionVo;
import com.lframework.xingyun.basedata.vo.product.property.QueryProductCategoryPropertyDefinitionVo;
import com.lframework.xingyun.basedata.vo.product.property.UpdateProductCategoryPropertyDefinitionVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCategoryPropertyDefinitionServiceImpl extends
    BaseMpServiceImpl<ProductCategoryPropertyDefinitionMapper, ProductCategoryPropertyDefinition>
    implements ProductCategoryPropertyDefinitionService {

  @Autowired
  private ProductCategoryPropertyValueRelationService ProductCategoryPropertyValueRelationService;

  @Override
  public PageResult<ProductCategoryPropertyDefinition> query(Integer pageIndex, Integer pageSize,
      QueryProductCategoryPropertyDefinitionVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductCategoryPropertyDefinition> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductCategoryPropertyDefinition> query(QueryProductCategoryPropertyDefinitionVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = ProductCategoryPropertyDefinition.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public ProductCategoryPropertyDefinition findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "删除商品分类属性，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Wrapper<ProductCategoryPropertyDefinition> updateWrapper = Wrappers.lambdaUpdate(ProductCategoryPropertyDefinition.class)
        .set(ProductCategoryPropertyDefinition::getAvailable, Boolean.FALSE).eq(ProductCategoryPropertyDefinition::getId, id);
    getBaseMapper().update(updateWrapper);

    ProductCategoryPropertyDefinition ProductCategoryPropertyDefinition = this.findById(id);

    DataChangeEventBuilder.publishLogicDelete(this, DeleteProductCategoryPropertyDefinitionEvent.class,
        ProductCategoryPropertyDefinition);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "新增商品分类属性，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateProductCategoryPropertyDefinitionVo vo) {

    Wrapper<ProductCategoryPropertyDefinition> checkCodeWrapper = Wrappers.lambdaQuery(ProductCategoryPropertyDefinition.class)
        .eq(ProductCategoryPropertyDefinition::getCode, vo.getCode()).eq(ProductCategoryPropertyDefinition::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductCategoryPropertyDefinition> checkNameWrapper = Wrappers.lambdaQuery(ProductCategoryPropertyDefinition.class)
        .eq(ProductCategoryPropertyDefinition::getName, vo.getName()).eq(ProductCategoryPropertyDefinition::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    ProductCategoryPropertyDefinition data = new ProductCategoryPropertyDefinition();
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
  public void update(UpdateProductCategoryPropertyDefinitionVo vo) {

    ProductCategoryPropertyDefinition data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("属性不存在！");
    }

    Wrapper<ProductCategoryPropertyDefinition> checkWrapper = Wrappers.lambdaQuery(ProductCategoryPropertyDefinition.class)
        .eq(ProductCategoryPropertyDefinition::getCode, vo.getCode()).eq(ProductCategoryPropertyDefinition::getAvailable, Boolean.TRUE)
        .ne(ProductCategoryPropertyDefinition::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductCategoryPropertyDefinition> checkNameWrapper = Wrappers.lambdaQuery(ProductCategoryPropertyDefinition.class)
        .eq(ProductCategoryPropertyDefinition::getName, vo.getName()).eq(ProductCategoryPropertyDefinition::getAvailable, Boolean.TRUE)
        .ne(ProductCategoryPropertyDefinition::getId, vo.getId());
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

    LambdaUpdateWrapper<ProductCategoryPropertyDefinition> updateWrapper = Wrappers.lambdaUpdate(
            ProductCategoryPropertyDefinition.class)
        .set(ProductCategoryPropertyDefinition::getCode, vo.getCode()).set(ProductCategoryPropertyDefinition::getName, vo.getName())
        .set(ProductCategoryPropertyDefinition::getIsRequired, vo.getIsRequired())
        .set(ProductCategoryPropertyDefinition::getColumnType, vo.getColumnType())
        .set(ProductCategoryPropertyDefinition::getPropertyType, PropertyType.APPOINT.getCode())
        .set(ProductCategoryPropertyDefinition::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(ProductCategoryPropertyDefinition::getId, vo.getId());
    if (vo.getColumnType() != ColumnType.CUSTOM.getCode().intValue()) {
      updateWrapper.set(ProductCategoryPropertyDefinition::getColumnDataType, null);
    } else {
      updateWrapper.set(ProductCategoryPropertyDefinition::getColumnDataType, ColumnDataType.STRING.getCode());
    }

    getBaseMapper().update(updateWrapper);

    if (data.getColumnType() == ColumnType.MULTIPLE
        && vo.getColumnType() == ColumnType.SINGLE.getCode()
        .intValue()) {
      //从多选更改为单选
      ProductCategoryPropertyValueRelationService.setMultipleToSimple(data.getId());
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public List<ProductCategoryPropertyDefinitionModelorDto> getModelorByCategoryId(String categoryId) {

    List<ProductCategoryPropertyDefinitionModelorDto> datas = getBaseMapper().getModelorByCategoryId(categoryId);
    return datas;
  }

  @CacheEvict(value = ProductCategoryPropertyDefinition.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
