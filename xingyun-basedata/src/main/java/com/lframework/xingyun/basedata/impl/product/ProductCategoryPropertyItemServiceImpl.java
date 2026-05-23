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
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyDefinition;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyItem;
import com.lframework.xingyun.basedata.enums.BaseDataOpLogType;
import com.lframework.xingyun.basedata.events.DeleteProductCategoryPropertyItemEvent;
import com.lframework.xingyun.basedata.mappers.ProductCategoryPropertyItemMapper;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyItemService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyDefinitionService;
import com.lframework.xingyun.basedata.vo.product.property.item.CreateProductCategoryPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.QueryProductCategoryPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.UpdateProductCategoryPropertyItemVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCategoryPropertyItemServiceImpl extends
    BaseMpServiceImpl<ProductCategoryPropertyItemMapper, ProductCategoryPropertyItem>
    implements ProductCategoryPropertyItemService {

  @Autowired
  private ProductCategoryPropertyDefinitionService ProductCategoryPropertyDefinitionService;

  @Override
  public PageResult<ProductCategoryPropertyItem> query(Integer pageIndex, Integer pageSize,
      QueryProductCategoryPropertyItemVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductCategoryPropertyItem> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductCategoryPropertyItem> query(QueryProductCategoryPropertyItemVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public List<ProductCategoryPropertyItem> getByPropertyId(String propertyId) {

    return getBaseMapper().getByPropertyId(propertyId);
  }

  @Cacheable(value = ProductCategoryPropertyItem.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public ProductCategoryPropertyItem findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = BaseDataOpLogType.class, name = "新增分类属性值，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateProductCategoryPropertyItemVo vo) {

    ProductCategoryPropertyDefinition property = ProductCategoryPropertyDefinitionService.findById(vo.getPropertyId());
    if (ObjectUtil.isNull(property)) {
      throw new DefaultClientException("属性不存在！");
    }

    Wrapper<ProductCategoryPropertyItem> checkWrapper = Wrappers.lambdaQuery(ProductCategoryPropertyItem.class)
        .eq(ProductCategoryPropertyItem::getPropertyId, vo.getPropertyId())
        .eq(ProductCategoryPropertyItem::getCode, vo.getCode())
        .eq(ProductCategoryPropertyItem::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductCategoryPropertyItem> checkNameWrapper = Wrappers.lambdaQuery(ProductCategoryPropertyItem.class)
        .eq(ProductCategoryPropertyItem::getPropertyId, vo.getPropertyId())
        .eq(ProductCategoryPropertyItem::getName, vo.getName())
        .eq(ProductCategoryPropertyItem::getAvailable, Boolean.TRUE);
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    ProductCategoryPropertyItem data = new ProductCategoryPropertyItem();
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

  @OpLog(type = BaseDataOpLogType.class, name = "修改分类属性值，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateProductCategoryPropertyItemVo vo) {

    ProductCategoryPropertyItem data = this.findById(vo.getId());
    if (data == null) {
      throw new DefaultClientException("属性值不存在！");
    }

    Wrapper<ProductCategoryPropertyItem> checkWrapper = Wrappers.lambdaQuery(ProductCategoryPropertyItem.class)
        .eq(ProductCategoryPropertyItem::getPropertyId, data.getPropertyId())
        .eq(ProductCategoryPropertyItem::getCode, vo.getCode())
        .eq(ProductCategoryPropertyItem::getAvailable, Boolean.TRUE)
        .ne(ProductCategoryPropertyItem::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductCategoryPropertyItem> checkNameWrapper = Wrappers.lambdaQuery(ProductCategoryPropertyItem.class)
        .eq(ProductCategoryPropertyItem::getPropertyId, data.getPropertyId())
        .eq(ProductCategoryPropertyItem::getName, vo.getName())
        .eq(ProductCategoryPropertyItem::getAvailable, Boolean.TRUE)
        .ne(ProductCategoryPropertyItem::getId, vo.getId());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    Wrapper<ProductCategoryPropertyItem> updateWrapper = Wrappers.lambdaUpdate(ProductCategoryPropertyItem.class)
        .set(ProductCategoryPropertyItem::getCode, vo.getCode())
        .set(ProductCategoryPropertyItem::getName, vo.getName())
        .eq(ProductCategoryPropertyItem::getId, vo.getId())
        .set(ProductCategoryPropertyItem::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Transactional(rollbackFor = Exception.class)
  @OpLog(type = BaseDataOpLogType.class, name = "删除分类属性值，ID：{}", params = "#id")
  @Override
  public void deleteById(String id) {

    Wrapper<ProductCategoryPropertyItem> deleteWrapper = Wrappers.lambdaUpdate(ProductCategoryPropertyItem.class)
        .eq(ProductCategoryPropertyItem::getId, id).set(ProductCategoryPropertyItem::getAvailable, Boolean.FALSE);
    this.update(deleteWrapper);

    ProductCategoryPropertyItem propertyItem = this.findById(id);

    DataChangeEventBuilder.publishLogicDelete(this, DeleteProductCategoryPropertyItemEvent.class,
        propertyItem);
  }

  @CacheEvict(value = ProductCategoryPropertyItem.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
