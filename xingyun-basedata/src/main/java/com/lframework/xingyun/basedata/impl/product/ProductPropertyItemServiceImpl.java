package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyDto;
import com.lframework.xingyun.basedata.dto.product.property.item.ProductPropertyItemDto;
import com.lframework.xingyun.basedata.entity.ProductPropertyItem;
import com.lframework.xingyun.basedata.mappers.ProductPropertyItemMapper;
import com.lframework.xingyun.basedata.service.product.IProductPropertyItemService;
import com.lframework.xingyun.basedata.service.product.IProductPropertyService;
import com.lframework.xingyun.basedata.vo.product.property.item.CreateProductPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.QueryProductPropertyItemVo;
import com.lframework.xingyun.basedata.vo.product.property.item.UpdateProductPropertyItemVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductPropertyItemServiceImpl implements IProductPropertyItemService {

  @Autowired
  private ProductPropertyItemMapper productPropertyItemMapper;

  @Autowired
  private IProductPropertyService productPropertyService;

  @Override
  public PageResult<ProductPropertyItemDto> query(Integer pageIndex, Integer pageSize,
      QueryProductPropertyItemVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductPropertyItemDto> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductPropertyItemDto> query(QueryProductPropertyItemVo vo) {

    return productPropertyItemMapper.query(vo);
  }

  @Override
  public List<ProductPropertyItemDto> getByPropertyId(String propertyId) {

    return productPropertyItemMapper.getByPropertyId(propertyId);
  }

  @Cacheable(value = ProductPropertyItemDto.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public ProductPropertyItemDto getById(String id) {

    return productPropertyItemMapper.getById(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增商品属性值，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(CreateProductPropertyItemVo vo) {

    ProductPropertyDto property = productPropertyService.getById(vo.getPropertyId());
    if (ObjectUtil.isNull(property)) {
      throw new DefaultClientException("属性不存在！");
    }

    Wrapper<ProductPropertyItem> checkWrapper = Wrappers.lambdaQuery(ProductPropertyItem.class)
        .eq(ProductPropertyItem::getPropertyId, vo.getPropertyId())
        .eq(ProductPropertyItem::getCode, vo.getCode());
    if (productPropertyItemMapper.selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductPropertyItem> checkNameWrapper = Wrappers.lambdaQuery(ProductPropertyItem.class)
        .eq(ProductPropertyItem::getPropertyId, vo.getPropertyId())
        .eq(ProductPropertyItem::getName, vo.getName());
    if (productPropertyItemMapper.selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    ProductPropertyItem data = new ProductPropertyItem();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setPropertyId(vo.getPropertyId());
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    productPropertyItemMapper.insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改商品属性值，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public void update(UpdateProductPropertyItemVo vo) {

    ProductPropertyItemDto data = this.getById(vo.getId());
    if (data == null) {
      throw new DefaultClientException("属性值不存在！");
    }

    Wrapper<ProductPropertyItem> checkWrapper = Wrappers.lambdaQuery(ProductPropertyItem.class)
        .eq(ProductPropertyItem::getPropertyId, data.getPropertyId())
        .eq(ProductPropertyItem::getCode, vo.getCode()).ne(ProductPropertyItem::getId, vo.getId());
    if (productPropertyItemMapper.selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductPropertyItem> checkNameWrapper = Wrappers.lambdaQuery(ProductPropertyItem.class)
        .eq(ProductPropertyItem::getPropertyId, data.getPropertyId())
        .eq(ProductPropertyItem::getName, vo.getName()).ne(ProductPropertyItem::getId, vo.getId());
    if (productPropertyItemMapper.selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    Wrapper<ProductPropertyItem> updateWrapper = Wrappers.lambdaUpdate(ProductPropertyItem.class)
        .set(ProductPropertyItem::getCode, vo.getCode())
        .set(ProductPropertyItem::getName, vo.getName())
        .set(ProductPropertyItem::getAvailable, vo.getAvailable())
        .eq(ProductPropertyItem::getId, vo.getId())
        .set(ProductPropertyItem::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    productPropertyItemMapper.update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    IProductPropertyItemService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(data.getId());
  }

  @CacheEvict(value = ProductPropertyItemDto.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(String key) {

  }
}
