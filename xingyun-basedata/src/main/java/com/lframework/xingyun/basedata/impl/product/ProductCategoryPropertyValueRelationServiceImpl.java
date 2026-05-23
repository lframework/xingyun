package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.xingyun.basedata.dto.product.ProductCategoryPropertyValueRelationDto;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyRelation;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyDefinition;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyItem;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyValueRelation;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.mappers.ProductCategoryPropertyValueRelationMapper;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyItemService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyValueRelationService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyDefinitionService;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.vo.product.property.relation.CreateProductCategoryPropertyValueRelationVo;
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
public class ProductCategoryPropertyValueRelationServiceImpl extends
    BaseMpServiceImpl<ProductCategoryPropertyValueRelationMapper, ProductCategoryPropertyValueRelation>
    implements ProductCategoryPropertyValueRelationService {

  @Autowired
  private ProductCategoryPropertyDefinitionService productCategoryPropertyDefinitionService;

  @Autowired
  private ProductCategoryPropertyItemService productCategoryPropertyItemService;

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductCategoryPropertyRelationService productCategoryPropertyRelationService;

  @Cacheable(value = ProductCategoryPropertyValueRelationDto.CACHE_NAME, key = "@cacheVariables.tenantId() + #productId", unless = "#result == null or #result.size() == 0")
  @Override
  public List<ProductCategoryPropertyValueRelationDto> getByProductId(String productId) {

    return getBaseMapper().getByProductId(productId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setMultipleToSimple(String propertyId) {

    List<ProductCategoryPropertyValueRelationDto> datas = getBaseMapper().getByPropertyId(propertyId);
    if (!CollectionUtil.isEmpty(datas)) {

      Set<ProductCategoryPropertyValueRelationDto> checkSet = new HashSet<>();
      for (ProductCategoryPropertyValueRelationDto data : datas) {
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

    List<ProductCategoryPropertyRelation> categoryList = productCategoryPropertyRelationService.getByPropertyId(
        propertyId);
    for (ProductCategoryPropertyRelation ProductCategoryPropertyRelationDto : categoryList) {
      getBaseMapper().setCommonToAppoint(propertyId, ProductCategoryPropertyRelationDto.getCategoryId());
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setAppointToCommon(String propertyId) {

    List<ProductCategoryPropertyItem> propertyItems = productCategoryPropertyItemService.getByPropertyId(
        propertyId);
    if (!CollectionUtil.isEmpty(propertyItems)) {
      ProductCategoryPropertyItem propertyItem = propertyItems.get(0);

      List<String> productIds = productService.getIdNotInProductCategoryPropertyDefinition(propertyId);
      if (!CollectionUtil.isEmpty(productIds)) {
        for (String productId : productIds) {
          ProductCategoryPropertyValueRelation data = new ProductCategoryPropertyValueRelation();
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

    List<ProductCategoryPropertyItem> propertyItems = productCategoryPropertyItemService.getByPropertyId(
        propertyId);
    if (!CollectionUtil.isEmpty(propertyItems)) {
      ProductCategoryPropertyItem propertyItem = propertyItems.get(0);

      Wrapper<ProductCategoryPropertyValueRelation> deleteWrapper = Wrappers.lambdaQuery(
              ProductCategoryPropertyValueRelation.class)
          .eq(ProductCategoryPropertyValueRelation::getPropertyId, propertyId);
      getBaseMapper().delete(deleteWrapper);

      List<ProductCategoryPropertyRelation> categoryList = productCategoryPropertyRelationService.getByPropertyId(
          propertyId);
      for (ProductCategoryPropertyRelation ProductCategoryPropertyRelationDto : categoryList) {
        List<String> productIds = productService.getIdByCategoryId(
            ProductCategoryPropertyRelationDto.getCategoryId());
        if (!CollectionUtil.isEmpty(productIds)) {
          for (String productId : productIds) {
            ProductCategoryPropertyValueRelation data = new ProductCategoryPropertyValueRelation();
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
  public String create(CreateProductCategoryPropertyValueRelationVo vo) {

    ProductCategoryPropertyDefinition productCategoryPropertyDefinition = productCategoryPropertyDefinitionService.findById(vo.getPropertyId());
    if (productCategoryPropertyDefinition == null) {
      throw new DefaultClientException("分类属性不存在！");
    }

    ProductCategoryPropertyValueRelation data = new ProductCategoryPropertyValueRelation();
    data.setId(IdUtil.getId());
    data.setProductId(vo.getProductId());
    data.setPropertyId(productCategoryPropertyDefinition.getId());

    if (productCategoryPropertyDefinition.getColumnType() != ColumnType.CUSTOM) {
      ProductCategoryPropertyItem propertyItem = productCategoryPropertyItemService.findById(
          vo.getPropertyItemId());

      if (propertyItem == null) {
        throw new DefaultClientException("属性值不存在！");
      }
      Assert.isTrue(propertyItem.getPropertyId().equals(productCategoryPropertyDefinition.getId()));

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
    Wrapper<ProductCategoryPropertyValueRelation> deleteWrapper = Wrappers.lambdaQuery(
        ProductCategoryPropertyValueRelation.class).eq(ProductCategoryPropertyValueRelation::getProductId, productId);
    getBaseMapper().delete(deleteWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByPropertyIdAndCategoryId(String propertyId, String categoryId) {

    getBaseMapper().deleteByPropertyIdAndCategoryId(propertyId, categoryId);
  }

  @CacheEvict(value = ProductCategoryPropertyValueRelationDto.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
