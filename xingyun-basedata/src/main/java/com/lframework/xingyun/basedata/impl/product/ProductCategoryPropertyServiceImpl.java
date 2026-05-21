package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.InputErrorException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.xingyun.basedata.dto.product.category.property.ProductCategoryPropertyDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductCategoryProperty;
import com.lframework.xingyun.basedata.entity.ProductProperty;
import com.lframework.xingyun.basedata.mappers.ProductCategoryPropertyMapper;
import com.lframework.xingyun.basedata.service.product.ProductCategoryService;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyRelationService;
import com.lframework.xingyun.basedata.service.product.ProductPropertyService;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductPropertyVo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCategoryPropertyServiceImpl
    extends BaseMpServiceImpl<ProductCategoryPropertyMapper, ProductCategoryProperty>
    implements ProductCategoryPropertyService {

  @Autowired
  private ProductCategoryService productCategoryService;

  @Lazy
  @Autowired
  private ProductPropertyService productPropertyService;

  @Lazy
  @Autowired
  private ProductPropertyRelationService productPropertyRelationService;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(String categoryId, String propertyId) {

    ProductCategoryProperty record = new ProductCategoryProperty();
    record.setId(IdUtil.getId());
    record.setPropertyId(propertyId);
    record.setCategoryId(categoryId);

    getBaseMapper().insert(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteByPropertyId(String propertyId) {

    Wrapper<ProductCategoryProperty> deleteWrapper = Wrappers.lambdaQuery(
            ProductCategoryProperty.class)
        .eq(ProductCategoryProperty::getPropertyId, propertyId);
    getBaseMapper().delete(deleteWrapper);
  }

  @Override
  public List<ProductCategoryProperty> getByPropertyId(String propertyId) {

    return getBaseMapper().getByPropertyId(propertyId);
  }

  @Override
  public List<ProductCategoryPropertyDto> getByCategoryId(String categoryId) {

    this.checkCategory(categoryId);
    return getBaseMapper().getByCategoryId(categoryId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void bindExistingProperties(String categoryId, List<String> propertyIds) {

    this.checkCategory(categoryId);
    if (CollectionUtil.isEmpty(propertyIds)) {
      throw new InputErrorException("请选择商品属性！");
    }

    List<String> distinctPropertyIds = propertyIds.stream().distinct().collect(Collectors.toList());
    for (String propertyId : distinctPropertyIds) {
      ProductProperty productProperty = productPropertyService.findById(propertyId);
      if (productProperty == null || !productProperty.getAvailable()) {
        throw new InputErrorException("商品属性数据有误，请检查！");
      }

      Wrapper<ProductCategoryProperty> checkWrapper = Wrappers.lambdaQuery(
              ProductCategoryProperty.class)
          .eq(ProductCategoryProperty::getCategoryId, categoryId)
          .eq(ProductCategoryProperty::getPropertyId, propertyId);
      if (getBaseMapper().selectCount(checkWrapper) == 0) {
        this.create(categoryId, propertyId);
      }
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String createPropertyAndBind(String categoryId, CreateProductPropertyVo vo) {

    this.checkCategory(categoryId);
    String propertyId = productPropertyService.create(vo);
    this.bindExistingProperties(categoryId, List.of(propertyId));

    return propertyId;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void removeByCategoryIdAndPropertyId(String categoryId, String propertyId) {

    this.checkCategory(categoryId);
    if (StringUtil.isBlank(propertyId)) {
      throw new InputErrorException("商品属性不能为空！");
    }

    Wrapper<ProductCategoryProperty> deleteWrapper = Wrappers.lambdaQuery(
            ProductCategoryProperty.class)
        .eq(ProductCategoryProperty::getCategoryId, categoryId)
        .eq(ProductCategoryProperty::getPropertyId, propertyId);
    getBaseMapper().delete(deleteWrapper);

    productPropertyRelationService.deleteByPropertyIdAndCategoryId(propertyId, categoryId);
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
