package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.product.category.PropertyCategoryCountDto;
import com.lframework.xingyun.basedata.dto.product.category.saleproperty.ProductCategorySalePropertyDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductCategorySaleProperty;
import com.lframework.xingyun.basedata.vo.product.saleproperty.CreateProductSalePropertyVo;
import java.util.List;

public interface ProductCategorySalePropertyService extends
    BaseMpService<ProductCategorySaleProperty> {

  String create(String categoryId, String propertyId);

  void deleteByPropertyId(String propertyId);

  List<ProductCategorySaleProperty> getByPropertyId(String propertyId);

  List<ProductCategory> getCategoriesByPropertyId(String propertyId);

  List<PropertyCategoryCountDto> countCategoriesByPropertyIds(List<String> propertyIds);

  List<ProductCategorySalePropertyDto> getByCategoryId(String categoryId);

  void bindExistingProperties(String categoryId, List<String> propertyIds);

  String createPropertyAndBind(String categoryId, CreateProductSalePropertyVo vo);

  void removeByCategoryIdAndPropertyId(String categoryId, String propertyId);
}
