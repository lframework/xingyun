package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.event.DataChangeEventBuilder;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.xingyun.basedata.dto.product.saleproperty.ProductSalePropertyModelorDto;
import com.lframework.xingyun.basedata.entity.ProductSaleProperty;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.xingyun.basedata.events.DeleteProductSalePropertyEvent;
import com.lframework.xingyun.basedata.mappers.ProductSalePropertyMapper;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyService;
import com.lframework.xingyun.basedata.vo.product.saleproperty.CreateProductSalePropertyVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.QueryProductSalePropertyVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.UpdateProductSalePropertyVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductSalePropertyServiceImpl extends
    BaseMpServiceImpl<ProductSalePropertyMapper, ProductSaleProperty>
    implements ProductSalePropertyService {

  @Override
  public PageResult<ProductSaleProperty> query(Integer pageIndex, Integer pageSize,
      QueryProductSalePropertyVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductSaleProperty> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductSaleProperty> query(QueryProductSalePropertyVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = ProductSaleProperty.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public ProductSaleProperty findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "删除商品销售属性，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Wrapper<ProductSaleProperty> updateWrapper = Wrappers.lambdaUpdate(
            ProductSaleProperty.class)
        .set(ProductSaleProperty::getAvailable, Boolean.FALSE)
        .eq(ProductSaleProperty::getId, id);
    getBaseMapper().update(updateWrapper);

    ProductSaleProperty productSaleProperty = this.findById(id);

    DataChangeEventBuilder.publishLogicDelete(this, DeleteProductSalePropertyEvent.class,
        productSaleProperty);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "新增商品销售属性，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateProductSalePropertyVo vo) {

    Wrapper<ProductSaleProperty> checkCodeWrapper = Wrappers.lambdaQuery(
            ProductSaleProperty.class)
        .eq(ProductSaleProperty::getCode, vo.getCode())
        .eq(ProductSaleProperty::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductSaleProperty> checkNameWrapper = Wrappers.lambdaQuery(
            ProductSaleProperty.class)
        .eq(ProductSaleProperty::getName, vo.getName())
        .eq(ProductSaleProperty::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    ProductSaleProperty data = new ProductSaleProperty();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = BaseDataOpLogType.class, name = "修改商品销售属性，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateProductSalePropertyVo vo) {

    ProductSaleProperty data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("商品销售属性不存在！");
    }

    Wrapper<ProductSaleProperty> checkWrapper = Wrappers.lambdaQuery(ProductSaleProperty.class)
        .eq(ProductSaleProperty::getCode, vo.getCode())
        .eq(ProductSaleProperty::getAvailable, Boolean.TRUE)
        .ne(ProductSaleProperty::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductSaleProperty> checkNameWrapper = Wrappers.lambdaQuery(
            ProductSaleProperty.class)
        .eq(ProductSaleProperty::getName, vo.getName())
        .eq(ProductSaleProperty::getAvailable, Boolean.TRUE)
        .ne(ProductSaleProperty::getId, vo.getId());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    LambdaUpdateWrapper<ProductSaleProperty> updateWrapper = Wrappers.lambdaUpdate(
            ProductSaleProperty.class)
        .set(ProductSaleProperty::getCode, vo.getCode())
        .set(ProductSaleProperty::getName, vo.getName())
        .set(ProductSaleProperty::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(ProductSaleProperty::getId, vo.getId());
    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public List<ProductSalePropertyModelorDto> getModelor() {

    return getBaseMapper().getModelor();
  }

  @CacheEvict(value = ProductSaleProperty.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
