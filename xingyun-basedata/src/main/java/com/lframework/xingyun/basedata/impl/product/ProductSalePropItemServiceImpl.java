package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.entity.ProductSalePropGroup;
import com.lframework.xingyun.basedata.entity.ProductSalePropItem;
import com.lframework.xingyun.basedata.mappers.ProductSalePropItemMapper;
import com.lframework.xingyun.basedata.service.product.IProductSalePropGroupService;
import com.lframework.xingyun.basedata.service.product.IProductSalePropItemService;
import com.lframework.xingyun.basedata.vo.product.saleprop.item.CreateProductSalePropItemVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.item.QueryProductSalePropItemSelectorVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.item.QueryProductSalePropItemVo;
import com.lframework.xingyun.basedata.vo.product.saleprop.item.UpdateProductSalePropItemVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductSalePropItemServiceImpl extends
    BaseMpServiceImpl<ProductSalePropItemMapper, ProductSalePropItem> implements
    IProductSalePropItemService {

  @Autowired
  private IProductSalePropGroupService productSalePropGroupService;

  @Override
  public PageResult<ProductSalePropItem> query(Integer pageIndex, Integer pageSize,
      QueryProductSalePropItemVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductSalePropItem> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public PageResult<ProductSalePropItem> selector(Integer pageIndex, Integer pageSize,
      QueryProductSalePropItemSelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductSalePropItem> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductSalePropItem> query(QueryProductSalePropItemVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = ProductSalePropItem.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public ProductSalePropItem findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增商品销售属性值，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(CreateProductSalePropItemVo vo) {

    Wrapper<ProductSalePropItem> checkWrapper = Wrappers.lambdaQuery(ProductSalePropItem.class)
        .eq(ProductSalePropItem::getGroupId, vo.getGroupId())
        .eq(ProductSalePropItem::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(ProductSalePropItem.class)
        .eq(ProductSalePropItem::getGroupId, vo.getGroupId())
        .eq(ProductSalePropItem::getName, vo.getName());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    ProductSalePropGroup productSalePropGroup = productSalePropGroupService.findById(
        vo.getGroupId());
    if (ObjectUtil.isNull(productSalePropGroup)) {
      throw new DefaultClientException("销售属性组不存在！");
    }

    ProductSalePropItem data = new ProductSalePropItem();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setGroupId(vo.getGroupId());
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改商品销售属性值，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public void update(UpdateProductSalePropItemVo vo) {

    ProductSalePropItem data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("销售属性值不存在！");
    }

    Wrapper<ProductSalePropItem> checkWrapper = Wrappers.lambdaQuery(ProductSalePropItem.class)
        .eq(ProductSalePropItem::getGroupId, data.getGroupId())
        .eq(ProductSalePropItem::getCode, vo.getCode()).ne(ProductSalePropItem::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(ProductSalePropItem.class)
        .eq(ProductSalePropItem::getGroupId, data.getGroupId())
        .eq(ProductSalePropItem::getName, vo.getName()).ne(ProductSalePropItem::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    LambdaUpdateWrapper<ProductSalePropItem> updateWrapper = Wrappers.lambdaUpdate(
            ProductSalePropItem.class).set(ProductSalePropItem::getCode, vo.getCode())
        .set(ProductSalePropItem::getName, vo.getName())
        .set(ProductSalePropItem::getAvailable, vo.getAvailable())
        .set(ProductSalePropItem::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(ProductSalePropItem::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public List<ProductSalePropItem> getEnablesByGroupId(String groupId) {

    return getBaseMapper().getEnablesByGroupId(groupId);
  }

  @CacheEvict(value = ProductSalePropItem.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
