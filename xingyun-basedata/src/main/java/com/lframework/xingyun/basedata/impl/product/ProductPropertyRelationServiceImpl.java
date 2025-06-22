package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.xingyun.basedata.dto.product.ProductPropertyRelationDto;
import com.lframework.xingyun.basedata.entity.ProductCategoryProperty;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.entity.ProductPropertyItem;
import com.lframework.xingyun.basedata.entity.ProductPropertyRelation;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.mappers.ProductPropertyRelationMapper;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyItemService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.vo.product.property.realtion.CreateProductPropertyRelationVo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductPropertyRelationServiceImpl extends
    BaseMpServiceImpl<ProductPropertyRelationMapper, ProductPropertyRelation>
    implements ProductPropertyRelationService {

  @Autowired
  private ProductPropertyService productPropertyService;

  @Autowired
  private ProductPropertyItemService productPropertyItemService;

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductCategoryPropertyService productCategoryPropertyService;

  @Cacheable(value = ProductPropertyRelationDto.CACHE_NAME, key = "@cacheVariables.tenantId() + #productId", unless = "#result == null or #result.size() == 0")
  @Override
  public List<ProductPropertyRelationDto> getByProductId(String productId) {

    return getBaseMapper().getByProductId(productId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setMultipleToSimple(String propertyId) {

    List<ProductPropertyRelationDto> datas = getBaseMapper().getByPropertyId(propertyId);
    if (!CollectionUtil.isEmpty(datas)) {

      Set<ProductPropertyRelationDto> checkSet = new HashSet<>();
      for (ProductPropertyRelationDto data : datas) {
        if (checkSet.stream()
            .anyMatch(t -> t.getProductId().equals(data.getProductId()) && t.getPropertyId()
                .equals(data.getPropertyId()))) {
          getBaseMapper().deleteById(data.getId());
        } else {
          checkSet.add(data);
        }
      }
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setCommonToAppoint(String propertyId) {

    List<ProductCategoryProperty> categoryList = productCategoryPropertyService.getByPropertyId(
        propertyId);
    for (ProductCategoryProperty productCategoryPropertyDto : categoryList) {
      getBaseMapper().setCommonToAppoint(propertyId, productCategoryPropertyDto.getCategoryId());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setAppointToCommon(String propertyId) {

    List<ProductPropertyItem> propertyItems = productPropertyItemService.getByPropertyId(
        propertyId);
    if (!CollectionUtil.isEmpty(propertyItems)) {
      ProductPropertyItem propertyItem = propertyItems.get(0);

      List<String> productIds = productService.getIdNotInProductProperty(propertyId);
      if (!CollectionUtil.isEmpty(productIds)) {
        for (String productId : productIds) {
          ProductPropertyRelation data = new ProductPropertyRelation();
          data.setId(IdUtil.getId());
          data.setProductId(productId);
          data.setPropertyId(propertyItem.getPropertyId());
          data.setPropertyItemId(propertyItem.getId());

          getBaseMapper().insert(data);
        }
      }
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateAppointCategoryId(String propertyId) {

    List<ProductPropertyItem> propertyItems = productPropertyItemService.getByPropertyId(
        propertyId);
    if (!CollectionUtil.isEmpty(propertyItems)) {
      ProductPropertyItem propertyItem = propertyItems.get(0);

      Wrapper<ProductPropertyRelation> deleteWrapper = Wrappers.lambdaQuery(
              ProductPropertyRelation.class)
          .eq(ProductPropertyRelation::getPropertyId, propertyId);
      getBaseMapper().delete(deleteWrapper);

      List<ProductCategoryProperty> categoryList = productCategoryPropertyService.getByPropertyId(
          propertyId);
      for (ProductCategoryProperty productCategoryPropertyDto : categoryList) {
        List<String> productIds = productService.getIdByCategoryId(
            productCategoryPropertyDto.getCategoryId());
        if (!CollectionUtil.isEmpty(productIds)) {
          for (String productId : productIds) {
            ProductPropertyRelation data = new ProductPropertyRelation();
            data.setId(IdUtil.getId());
            data.setProductId(productId);
            data.setPropertyId(propertyItem.getPropertyId());
            data.setPropertyItemId(propertyItem.getId());

            getBaseMapper().insert(data);
          }
        }
      }
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateProductPropertyRelationVo vo) {

    ProductProperty productProperty = productPropertyService.findById(vo.getPropertyId());
    if (productProperty == null) {
      throw new DefaultClientException("商品属性不存在！");
    }

    ProductPropertyRelation data = new ProductPropertyRelation();
    data.setId(IdUtil.getId());
    data.setProductId(vo.getProductId());
    data.setPropertyId(productProperty.getId());

    if (productProperty.getColumnType() != ColumnType.CUSTOM) {
      ProductPropertyItem propertyItem = productPropertyItemService.findById(
          vo.getPropertyItemId());

      if (propertyItem == null) {
        throw new DefaultClientException("属性值不存在！");
      }
      Assert.isTrue(propertyItem.getPropertyId().equals(productProperty.getId()));

      data.setPropertyItemId(propertyItem.getId());

    } else {
      if (StringUtil.isBlank(vo.getPropertyText())) {
        throw new DefaultClientException("属性值文本不存在！");
      }

      data.setPropertyText(vo.getPropertyText());
    }

    getBaseMapper().insert(data);

    return data.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByProductId(String productId) {
    Wrapper<ProductPropertyRelation> deleteWrapper = Wrappers.lambdaQuery(
        ProductPropertyRelation.class).eq(ProductPropertyRelation::getProductId, productId);
    getBaseMapper().delete(deleteWrapper);
  }

  @CacheEvict(value = ProductPropertyRelationDto.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
