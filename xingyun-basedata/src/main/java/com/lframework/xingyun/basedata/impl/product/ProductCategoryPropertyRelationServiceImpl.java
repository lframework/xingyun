package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.xingyun.basedata.dto.product.category.PropertyCategoryCountDto;
import com.lframework.xingyun.basedata.dto.product.category.property.ProductCategoryPropertyRelationDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyRelation;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyDefinition;
import com.lframework.xingyun.basedata.mappers.ProductCategoryPropertyRelationMapper;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyValueRelationService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyDefinitionService;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductCategoryPropertyDefinitionVo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCategoryPropertyRelationServiceImpl
    extends BaseMpServiceImpl<ProductCategoryPropertyRelationMapper, ProductCategoryPropertyRelation>
    implements ProductCategoryPropertyRelationService {

  @Autowired
  private ProductCategoryService productCategoryService;

  @Lazy
  @Autowired
  private ProductCategoryPropertyDefinitionService ProductCategoryPropertyDefinitionService;

  @Lazy
  @Autowired
  private ProductCategoryPropertyValueRelationService ProductCategoryPropertyValueRelationService;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(String categoryId, String propertyId) {

    ProductCategoryPropertyRelation record = new ProductCategoryPropertyRelation();
    record.setId(IdUtil.getId());
    record.setPropertyId(propertyId);
    record.setCategoryId(categoryId);

    getBaseMapper().insert(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByPropertyId(String propertyId) {

    Wrapper<ProductCategoryPropertyRelation> deleteWrapper = Wrappers.lambdaQuery(
            ProductCategoryPropertyRelation.class)
        .eq(ProductCategoryPropertyRelation::getPropertyId, propertyId);
    getBaseMapper().delete(deleteWrapper);
  }

  @Override
  public List<ProductCategoryPropertyRelation> getByPropertyId(String propertyId) {

    return getBaseMapper().getByPropertyId(propertyId);
  }

  @Override
  public List<ProductCategory> getCategoriesByPropertyId(String propertyId) {

    if (StringUtil.isBlank(propertyId)) {
      throw new InputErrorException("分类属性不能为空！");
    }

    return getBaseMapper().getCategoriesByPropertyId(propertyId);
  }

  @Override
  public List<PropertyCategoryCountDto> countCategoriesByPropertyIds(List<String> propertyIds) {

    if (CollectionUtil.isEmpty(propertyIds)) {
      return CollectionUtil.emptyList();
    }

    return getBaseMapper().countCategoriesByPropertyIds(propertyIds);
  }

  @Override
  public List<ProductCategoryPropertyRelationDto> getByCategoryId(String categoryId) {

    this.checkCategory(categoryId);
    return getBaseMapper().getByCategoryId(categoryId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void bindExistingProperties(String categoryId, List<String> propertyIds) {

    this.checkCategory(categoryId);
    if (CollectionUtil.isEmpty(propertyIds)) {
      throw new InputErrorException("请选择分类属性！");
    }

    List<String> distinctPropertyIds = propertyIds.stream().distinct().collect(Collectors.toList());
    for (String propertyId : distinctPropertyIds) {
      ProductCategoryPropertyDefinition ProductCategoryPropertyDefinition = ProductCategoryPropertyDefinitionService.findById(propertyId);
      if (ProductCategoryPropertyDefinition == null || !ProductCategoryPropertyDefinition.getAvailable()) {
        throw new InputErrorException("分类属性数据有误，请检查！");
      }

      Wrapper<ProductCategoryPropertyRelation> checkWrapper = Wrappers.lambdaQuery(
              ProductCategoryPropertyRelation.class)
          .eq(ProductCategoryPropertyRelation::getCategoryId, categoryId)
          .eq(ProductCategoryPropertyRelation::getPropertyId, propertyId);
      if (getBaseMapper().selectCount(checkWrapper) == 0) {
        this.create(categoryId, propertyId);
      }
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String createPropertyAndBind(String categoryId, CreateProductCategoryPropertyDefinitionVo vo) {

    this.checkCategory(categoryId);
    String propertyId = ProductCategoryPropertyDefinitionService.create(vo);
    this.bindExistingProperties(categoryId, List.of(propertyId));

    return propertyId;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void removeByCategoryIdAndPropertyId(String categoryId, String propertyId) {

    this.checkCategory(categoryId);
    if (StringUtil.isBlank(propertyId)) {
      throw new InputErrorException("分类属性不能为空！");
    }

    Wrapper<ProductCategoryPropertyRelation> deleteWrapper = Wrappers.lambdaQuery(
            ProductCategoryPropertyRelation.class)
        .eq(ProductCategoryPropertyRelation::getCategoryId, categoryId)
        .eq(ProductCategoryPropertyRelation::getPropertyId, propertyId);
    getBaseMapper().delete(deleteWrapper);

    ProductCategoryPropertyValueRelationService.deleteByPropertyIdAndCategoryId(propertyId, categoryId);
  }

  private void checkCategory(String categoryId) {

    if (StringUtil.isBlank(categoryId)) {
      throw new InputErrorException("商品分类不能为空！");
    }

    ProductCategory productCategory = productCategoryService.findById(categoryId);
    if (productCategory == null || !productCategory.getAvailable()) {
      throw new DefaultClientException("商品分类不存在！");
    }

    Wrapper<ProductCategory> checkCategoryWrapper = Wrappers.lambdaQuery(
            ProductCategory.class).eq(ProductCategory::getParentId, productCategory.getId())
        .eq(ProductCategory::getAvailable, Boolean.TRUE);
    if (productCategoryService.count(checkCategoryWrapper) > 0) {
      throw new DefaultClientException(
          "“商品分类”不是末级分类，请选择末级分类");
    }
  }
}
