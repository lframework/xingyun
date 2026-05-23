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
import com.lframework.xingyun.basedata.dto.product.category.saleproperty.ProductCategorySalePropertyRelationDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductCategorySalePropertyRelation;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyDefinition;
import com.lframework.xingyun.basedata.enums.ProductSkuType;
import com.lframework.xingyun.basedata.mappers.ProductMapper;
import com.lframework.xingyun.basedata.mappers.ProductCategorySalePropertyRelationMapper;
import com.lframework.xingyun.basedata.service.product.ProductCategorySalePropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyDefinitionService;
import com.lframework.xingyun.basedata.vo.product.saleproperty.CreateProductSalePropertyDefinitionVo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCategorySalePropertyRelationServiceImpl
    extends BaseMpServiceImpl<ProductCategorySalePropertyRelationMapper, ProductCategorySalePropertyRelation>
    implements ProductCategorySalePropertyRelationService {

  @Autowired
  private ProductCategoryService productCategoryService;

  @Lazy
  @Autowired
  private ProductSalePropertyDefinitionService ProductSalePropertyDefinitionService;

  @Autowired
  private ProductMapper productMapper;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(String categoryId, String propertyId) {

    this.checkCategoryHasMultiSkuProduct(categoryId);

    ProductCategorySalePropertyRelation record = new ProductCategorySalePropertyRelation();
    record.setId(IdUtil.getId());
    record.setPropertyId(propertyId);
    record.setCategoryId(categoryId);

    getBaseMapper().insert(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByPropertyId(String propertyId) {

    Wrapper<ProductCategorySalePropertyRelation> deleteWrapper = Wrappers.lambdaQuery(
            ProductCategorySalePropertyRelation.class)
        .eq(ProductCategorySalePropertyRelation::getPropertyId, propertyId);
    getBaseMapper().delete(deleteWrapper);
  }

  @Override
  public List<ProductCategorySalePropertyRelation> getByPropertyId(String propertyId) {

    return getBaseMapper().getByPropertyId(propertyId);
  }

  @Override
  public List<ProductCategory> getCategoriesByPropertyId(String propertyId) {

    if (StringUtil.isBlank(propertyId)) {
      throw new InputErrorException("销售属性不能为空！");
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
  public List<ProductCategorySalePropertyRelationDto> getByCategoryId(String categoryId) {

    this.checkCategory(categoryId);
    return getBaseMapper().getByCategoryId(categoryId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void bindExistingProperties(String categoryId, List<String> propertyIds) {

    this.checkCategory(categoryId);
    this.checkCategoryHasMultiSkuProduct(categoryId);
    if (CollectionUtil.isEmpty(propertyIds)) {
      throw new InputErrorException("请选择销售属性！");
    }

    List<String> distinctPropertyIds = propertyIds.stream().distinct().collect(Collectors.toList());
    for (String propertyId : distinctPropertyIds) {
      ProductSalePropertyDefinition ProductSalePropertyDefinition = ProductSalePropertyDefinitionService.findById(propertyId);
      if (ProductSalePropertyDefinition == null || !ProductSalePropertyDefinition.getAvailable()) {
        throw new InputErrorException("销售属性数据有误，请检查！");
      }

      Wrapper<ProductCategorySalePropertyRelation> checkWrapper = Wrappers.lambdaQuery(
              ProductCategorySalePropertyRelation.class)
          .eq(ProductCategorySalePropertyRelation::getCategoryId, categoryId)
          .eq(ProductCategorySalePropertyRelation::getPropertyId, propertyId);
      if (getBaseMapper().selectCount(checkWrapper) == 0) {
        this.create(categoryId, propertyId);
      }
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String createPropertyAndBind(String categoryId, CreateProductSalePropertyDefinitionVo vo) {

    this.checkCategory(categoryId);
    this.checkCategoryHasMultiSkuProduct(categoryId);
    String propertyId = ProductSalePropertyDefinitionService.create(vo);
    this.bindExistingProperties(categoryId, List.of(propertyId));

    return propertyId;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void removeByCategoryIdAndPropertyId(String categoryId, String propertyId) {

    this.checkCategory(categoryId);
    this.checkCategoryHasMultiSkuProduct(categoryId);
    if (StringUtil.isBlank(propertyId)) {
      throw new InputErrorException("销售属性不能为空！");
    }

    Wrapper<ProductCategorySalePropertyRelation> deleteWrapper = Wrappers.lambdaQuery(
            ProductCategorySalePropertyRelation.class)
        .eq(ProductCategorySalePropertyRelation::getCategoryId, categoryId)
        .eq(ProductCategorySalePropertyRelation::getPropertyId, propertyId);
    getBaseMapper().delete(deleteWrapper);
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

  private void checkCategoryHasMultiSkuProduct(String categoryId) {

    Wrapper<Product> wrapper = Wrappers.lambdaQuery(Product.class)
        .eq(Product::getCategoryId, categoryId)
        .eq(Product::getSkuType, ProductSkuType.MULTI)
        .eq(Product::getAvailable, Boolean.TRUE);
    if (productMapper.selectCount(wrapper) > 0) {
      throw new DefaultClientException("该商品分类已关联多SKU商品，不允许添加或移除销售属性！");
    }
  }
}
