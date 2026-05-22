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
import com.lframework.xingyun.basedata.dto.product.category.saleproperty.ProductCategorySalePropertyDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductCategorySaleProperty;
import com.lframework.xingyun.basedata.entity.ProductSaleProperty;
import com.lframework.xingyun.basedata.mappers.ProductCategorySalePropertyMapper;
import com.lframework.xingyun.basedata.service.product.ProductCategorySalePropertyService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductSalePropertyService;
import com.lframework.xingyun.basedata.vo.product.saleproperty.CreateProductSalePropertyVo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCategorySalePropertyServiceImpl
    extends BaseMpServiceImpl<ProductCategorySalePropertyMapper, ProductCategorySaleProperty>
    implements ProductCategorySalePropertyService {

  @Autowired
  private ProductCategoryService productCategoryService;

  @Lazy
  @Autowired
  private ProductSalePropertyService productSalePropertyService;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(String categoryId, String propertyId) {

    ProductCategorySaleProperty record = new ProductCategorySaleProperty();
    record.setId(IdUtil.getId());
    record.setPropertyId(propertyId);
    record.setCategoryId(categoryId);

    getBaseMapper().insert(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByPropertyId(String propertyId) {

    Wrapper<ProductCategorySaleProperty> deleteWrapper = Wrappers.lambdaQuery(
            ProductCategorySaleProperty.class)
        .eq(ProductCategorySaleProperty::getPropertyId, propertyId);
    getBaseMapper().delete(deleteWrapper);
  }

  @Override
  public List<ProductCategorySaleProperty> getByPropertyId(String propertyId) {

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
  public List<ProductCategorySalePropertyDto> getByCategoryId(String categoryId) {

    this.checkCategory(categoryId);
    return getBaseMapper().getByCategoryId(categoryId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void bindExistingProperties(String categoryId, List<String> propertyIds) {

    this.checkCategory(categoryId);
    if (CollectionUtil.isEmpty(propertyIds)) {
      throw new InputErrorException("请选择销售属性！");
    }

    List<String> distinctPropertyIds = propertyIds.stream().distinct().collect(Collectors.toList());
    for (String propertyId : distinctPropertyIds) {
      ProductSaleProperty productSaleProperty = productSalePropertyService.findById(propertyId);
      if (productSaleProperty == null || !productSaleProperty.getAvailable()) {
        throw new InputErrorException("销售属性数据有误，请检查！");
      }

      Wrapper<ProductCategorySaleProperty> checkWrapper = Wrappers.lambdaQuery(
              ProductCategorySaleProperty.class)
          .eq(ProductCategorySaleProperty::getCategoryId, categoryId)
          .eq(ProductCategorySaleProperty::getPropertyId, propertyId);
      if (getBaseMapper().selectCount(checkWrapper) == 0) {
        this.create(categoryId, propertyId);
      }
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String createPropertyAndBind(String categoryId, CreateProductSalePropertyVo vo) {

    this.checkCategory(categoryId);
    String propertyId = productSalePropertyService.create(vo);
    this.bindExistingProperties(categoryId, List.of(propertyId));

    return propertyId;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void removeByCategoryIdAndPropertyId(String categoryId, String propertyId) {

    this.checkCategory(categoryId);
    if (StringUtil.isBlank(propertyId)) {
      throw new InputErrorException("销售属性不能为空！");
    }

    Wrapper<ProductCategorySaleProperty> deleteWrapper = Wrappers.lambdaQuery(
            ProductCategorySaleProperty.class)
        .eq(ProductCategorySaleProperty::getCategoryId, categoryId)
        .eq(ProductCategorySaleProperty::getPropertyId, propertyId);
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
}
