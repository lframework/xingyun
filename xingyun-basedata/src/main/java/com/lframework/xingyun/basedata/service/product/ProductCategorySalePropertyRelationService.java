package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.product.category.PropertyCategoryCountDto;
import com.lframework.xingyun.basedata.dto.product.category.saleproperty.ProductCategorySalePropertyRelationDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductCategorySalePropertyRelation;
import com.lframework.xingyun.basedata.vo.product.saleproperty.CreateProductSalePropertyDefinitionVo;
import java.util.List;

public interface ProductCategorySalePropertyRelationService extends
    BaseMpService<ProductCategorySalePropertyRelation> {

  String create(String categoryId, String propertyId);

  void deleteByPropertyId(String propertyId);

  List<ProductCategorySalePropertyRelation> getByPropertyId(String propertyId);

  List<ProductCategory> getCategoriesByPropertyId(String propertyId);

  List<PropertyCategoryCountDto> countCategoriesByPropertyIds(List<String> propertyIds);

  List<ProductCategorySalePropertyRelationDto> getByCategoryId(String categoryId);

  void bindExistingProperties(String categoryId, List<String> propertyIds);

  String createPropertyAndBind(String categoryId, CreateProductSalePropertyDefinitionVo vo);

  void removeByCategoryIdAndPropertyId(String categoryId, String propertyId);
}
