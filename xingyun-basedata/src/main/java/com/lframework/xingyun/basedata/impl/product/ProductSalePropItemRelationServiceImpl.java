package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.SalePropItemByProductDto;
import com.lframework.xingyun.basedata.entity.ProductSalePropGroup;
import com.lframework.xingyun.basedata.entity.ProductSalePropItem;
import com.lframework.xingyun.basedata.entity.ProductSalePropItemRelation;
import com.lframework.xingyun.basedata.mappers.ProductSalePropItemRelationMapper;
import com.lframework.xingyun.basedata.service.product.IProductSalePropGroupService;
import com.lframework.xingyun.basedata.service.product.IProductSalePropItemRelationService;
import com.lframework.xingyun.basedata.service.product.IProductSalePropItemService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.saleprop.CreateProductSalePropItemRelationVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductSalePropItemRelationServiceImpl extends
    BaseMpServiceImpl<ProductSalePropItemRelationMapper, ProductSalePropItemRelation> implements
    IProductSalePropItemRelationService {

  @Autowired
  private IProductService productService;

  @Autowired
  private IProductSalePropItemService productSalePropItemService;

  @Autowired
  private IProductSalePropGroupService productSalePropGroupService;

  @Cacheable(value = ProductSalePropItemRelation.CACHE_NAME_BY_PRODUCT_ID, key = "#productId", unless = "#result == null")
  @Override
  public SalePropItemByProductDto getByProductId(String productId) {
    return getBaseMapper().getByProductId(productId);
  }

  @Override
  public List<String> getProductIdById(String id) {
    return getBaseMapper().getProductIdById(id);
  }

  @Transactional
  @Override
  public void create(CreateProductSalePropItemRelationVo vo) {

    ProductDto product = productService.findById(vo.getProductId());
    if (product == null) {
      throw new DefaultClientException("??????????????????");
    }

    if (vo.getSalePropItemIds().size() == 1) {
      Wrapper<ProductSalePropItemRelation> queryWrapper = Wrappers.lambdaQuery(
              ProductSalePropItemRelation.class)
          .eq(ProductSalePropItemRelation::getPolyId, product.getPoly().getId())
          .eq(ProductSalePropItemRelation::getSalePropItemId1, vo.getSalePropItemIds().get(0))
          .ne(ProductSalePropItemRelation::getProductId, product.getId());
      if (this.count(queryWrapper) > 0) {
        throw new DefaultClientException(
            "?????????" + product.getName() + "???????????????1???????????????????????????????????????????????????????????????????????????");
      }
    } else if (vo.getSalePropItemIds().size() == 2) {
      Wrapper<ProductSalePropItemRelation> queryWrapper = Wrappers.lambdaQuery(
              ProductSalePropItemRelation.class)
          .eq(ProductSalePropItemRelation::getPolyId, product.getPoly().getId())
          .eq(ProductSalePropItemRelation::getSalePropItemId1, vo.getSalePropItemIds().get(0))
          .eq(ProductSalePropItemRelation::getSalePropItemId2, vo.getSalePropItemIds().get(1))
          .ne(ProductSalePropItemRelation::getProductId, product.getId());
      if (this.count(queryWrapper) > 0) {
        throw new DefaultClientException(
            "?????????" + product.getName() + "???????????????1???????????????2???????????????????????????????????????????????????????????????????????????");
      }
    } else {
      throw new DefaultSysException("?????????????????????????????????");
    }

    String salePropItemId1 = null;
    String salePropItemId2 = null;
    String salePropGroupId1 = null;
    String salePropGroupId2 = null;
    int orderNo = 1;
    for (String salePropItemId : vo.getSalePropItemIds()) {

      ProductSalePropItem salePropItem = productSalePropItemService.findById(salePropItemId);
      if (salePropItem == null) {
        throw new DefaultClientException("????????????" + orderNo + "????????????");
      }

      if (orderNo == 1) {
        salePropItemId1 = salePropItemId;
        ProductSalePropGroup salePropGroup = productSalePropGroupService.getById(
            salePropItem.getGroupId());
        if (salePropGroup == null) {
          throw new DefaultClientException("???????????????1????????????");
        }
        salePropGroupId1 = salePropGroup.getId();
      } else {
        salePropItemId2 = salePropItemId;
        ProductSalePropGroup salePropGroup = productSalePropGroupService.getById(
            salePropItem.getGroupId());
        if (salePropGroup == null) {
          throw new DefaultClientException("???????????????2????????????");
        }
        salePropGroupId2 = salePropGroup.getId();
      }
      orderNo++;
    }

    Wrapper<ProductSalePropItemRelation> deleteWrapper = Wrappers.lambdaQuery(
            ProductSalePropItemRelation.class)
        .eq(ProductSalePropItemRelation::getProductId, product.getId());
    this.remove(deleteWrapper);

    ProductSalePropItemRelation data = new ProductSalePropItemRelation();
    data.setId(IdUtil.getId());
    data.setPolyId(product.getPoly().getId());
    data.setProductId(product.getId());
    data.setSalePropGroupId1(salePropGroupId1);
    data.setSalePropGroupId2(salePropGroupId2);
    data.setSalePropItemId1(salePropItemId1);
    data.setSalePropItemId2(salePropItemId2);

    getBaseMapper().insert(data);
  }

  @CacheEvict(value = {ProductSalePropItemRelation.CACHE_NAME,
      ProductSalePropItemRelation.CACHE_NAME_BY_PRODUCT_ID}, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {
    super.cleanCacheByKey(key);
  }
}
