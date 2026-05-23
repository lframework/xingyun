package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
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
import com.lframework.xingyun.basedata.entity.ProductSalePropertyDefinition;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyItem;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.xingyun.basedata.events.DeleteProductSalePropertyItemEvent;
import com.lframework.xingyun.basedata.mappers.ProductSalePropertyItemMapper;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyItemService;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyDefinitionService;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import com.lframework.xingyun.basedata.vo.product.saleproperty.item.CreateProductSalePropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.item.QueryProductSalePropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.saleproperty.item.UpdateProductSalePropertyItemVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductSalePropertyItemServiceImpl extends
    BaseMpServiceImpl<ProductSalePropertyItemMapper, ProductSalePropertyItem>
    implements ProductSalePropertyItemService {

  @Autowired
  private ProductSalePropertyDefinitionService ProductSalePropertyDefinitionService;

  @Autowired
  private ProductSkuService productSkuService;

  @Override
  public PageResult<ProductSalePropertyItem> query(Integer pageIndex, Integer pageSize,
      QueryProductSalePropertyItemVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductSalePropertyItem> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductSalePropertyItem> query(QueryProductSalePropertyItemVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public List<ProductSalePropertyItem> getByPropertyId(String propertyId) {

    return getBaseMapper().getByPropertyId(propertyId);
  }

  @Cacheable(value = ProductSalePropertyItem.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public ProductSalePropertyItem findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "新增销售属性值，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateProductSalePropertyItemVo vo) {

    ProductSalePropertyDefinition property = ProductSalePropertyDefinitionService.findById(vo.getPropertyId());
    if (ObjectUtil.isNull(property)) {
      throw new DefaultClientException("商品销售属性不存在！");
    }

    Wrapper<ProductSalePropertyItem> checkWrapper = Wrappers.lambdaQuery(
            ProductSalePropertyItem.class)
        .eq(ProductSalePropertyItem::getPropertyId, vo.getPropertyId())
        .eq(ProductSalePropertyItem::getCode, vo.getCode())
        .eq(ProductSalePropertyItem::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductSalePropertyItem> checkNameWrapper = Wrappers.lambdaQuery(
            ProductSalePropertyItem.class)
        .eq(ProductSalePropertyItem::getPropertyId, vo.getPropertyId())
        .eq(ProductSalePropertyItem::getName, vo.getName())
        .eq(ProductSalePropertyItem::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    ProductSalePropertyItem data = new ProductSalePropertyItem();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setPropertyId(vo.getPropertyId());
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = BaseDataOpLogType.class, name = "修改销售属性值，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateProductSalePropertyItemVo vo) {

    ProductSalePropertyItem data = this.findById(vo.getId());
    if (data == null) {
      throw new DefaultClientException("销售属性值不存在！");
    }

    Wrapper<ProductSalePropertyItem> checkWrapper = Wrappers.lambdaQuery(
            ProductSalePropertyItem.class)
        .eq(ProductSalePropertyItem::getPropertyId, data.getPropertyId())
        .eq(ProductSalePropertyItem::getCode, vo.getCode())
        .eq(ProductSalePropertyItem::getAvailable, Boolean.TRUE)
        .ne(ProductSalePropertyItem::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductSalePropertyItem> checkNameWrapper = Wrappers.lambdaQuery(
            ProductSalePropertyItem.class)
        .eq(ProductSalePropertyItem::getPropertyId, data.getPropertyId())
        .eq(ProductSalePropertyItem::getName, vo.getName())
        .eq(ProductSalePropertyItem::getAvailable, Boolean.TRUE)
        .ne(ProductSalePropertyItem::getId, vo.getId());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    Wrapper<ProductSalePropertyItem> updateWrapper = Wrappers.lambdaUpdate(
            ProductSalePropertyItem.class)
        .set(ProductSalePropertyItem::getCode, vo.getCode())
        .set(ProductSalePropertyItem::getName, vo.getName())
        .eq(ProductSalePropertyItem::getId, vo.getId())
        .set(ProductSalePropertyItem::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    getBaseMapper().update(updateWrapper);
    productSkuService.refreshSalePropertyTextByPropertyId(data.getPropertyId());

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Transactional(rollbackFor = Exception.class)
  @OpLog(type = BaseDataOpLogType.class, name = "删除销售属性值，ID：{}", params = "#id")
  @Override
  public void deleteById(String id) {

    Wrapper<ProductSalePropertyItem> deleteWrapper = Wrappers.lambdaUpdate(
            ProductSalePropertyItem.class)
        .eq(ProductSalePropertyItem::getId, id)
        .set(ProductSalePropertyItem::getAvailable, Boolean.FALSE);
    this.update(deleteWrapper);

    ProductSalePropertyItem item = this.findById(id);
    DataChangeEventBuilder.publishLogicDelete(this, DeleteProductSalePropertyItemEvent.class,
        item);
  }

  @CacheEvict(value = ProductSalePropertyItem.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
