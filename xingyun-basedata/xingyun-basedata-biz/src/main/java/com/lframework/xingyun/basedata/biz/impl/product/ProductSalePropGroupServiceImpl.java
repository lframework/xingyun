package com.lframework.xingyun.basedata.biz.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
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
import com.lframework.xingyun.basedata.biz.mappers.ProductSalePropGroupMapper;
import com.lframework.xingyun.basedata.biz.service.product.IProductSalePropGroupService;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropGroup;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.CreateProductSalePropGroupVo;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.QueryProductSalePropGroupSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.QueryProductSalePropGroupVo;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.UpdateProductSalePropGroupVo;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductSalePropGroupServiceImpl extends
    BaseMpServiceImpl<ProductSalePropGroupMapper, ProductSalePropGroup>
    implements IProductSalePropGroupService {

  @Override
  public PageResult<ProductSalePropGroup> query(Integer pageIndex, Integer pageSize,
      QueryProductSalePropGroupVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductSalePropGroup> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductSalePropGroup> query(QueryProductSalePropGroupVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<ProductSalePropGroup> selector(Integer pageIndex, Integer pageSize,
      QueryProductSalePropGroupSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductSalePropGroup> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = ProductSalePropGroup.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public ProductSalePropGroup findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "停用商品销售属性，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<ProductSalePropGroup> updateWrapper = Wrappers.lambdaUpdate(ProductSalePropGroup.class)
        .set(ProductSalePropGroup::getAvailable, Boolean.FALSE)
        .in(ProductSalePropGroup::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = OpLogType.OTHER, name = "启用商品销售属性，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<ProductSalePropGroup> updateWrapper = Wrappers.lambdaUpdate(ProductSalePropGroup.class)
        .set(ProductSalePropGroup::getAvailable, Boolean.TRUE).in(ProductSalePropGroup::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增商品销售属性组，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(CreateProductSalePropGroupVo vo) {

    Wrapper<ProductSalePropGroup> checkWrapper = Wrappers.lambdaQuery(ProductSalePropGroup.class)
        .eq(ProductSalePropGroup::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(ProductSalePropGroup.class)
        .eq(ProductSalePropGroup::getName, vo.getName());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    ProductSalePropGroup data = new ProductSalePropGroup();
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

  @OpLog(type = OpLogType.OTHER, name = "修改商品销售属性组，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public void update(UpdateProductSalePropGroupVo vo) {

    ProductSalePropGroup data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("销售属性组不存在！");
    }

    Wrapper<ProductSalePropGroup> checkWrapper = Wrappers.lambdaQuery(ProductSalePropGroup.class)
        .eq(ProductSalePropGroup::getCode, vo.getCode())
        .ne(ProductSalePropGroup::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(ProductSalePropGroup.class)
        .eq(ProductSalePropGroup::getName, vo.getName())
        .ne(ProductSalePropGroup::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    LambdaUpdateWrapper<ProductSalePropGroup> updateWrapper = Wrappers.lambdaUpdate(
            ProductSalePropGroup.class)
        .set(ProductSalePropGroup::getCode, vo.getCode())
        .set(ProductSalePropGroup::getName, vo.getName())
        .set(ProductSalePropGroup::getAvailable, vo.getAvailable())
        .set(ProductSalePropGroup::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(ProductSalePropGroup::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @CacheEvict(value = ProductSalePropGroup.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
