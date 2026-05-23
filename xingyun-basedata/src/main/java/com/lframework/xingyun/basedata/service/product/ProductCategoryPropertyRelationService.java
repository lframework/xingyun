package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.product.category.PropertyCategoryCountDto;
import com.lframework.xingyun.basedata.dto.product.category.property.ProductCategoryPropertyRelationDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyRelation;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductCategoryPropertyDefinitionVo;
import java.util.List;

public interface ProductCategoryPropertyRelationService extends BaseMpService<ProductCategoryPropertyRelation> {

  /**
   * 创建
   *
   * @param categoryId
   * @param propertyId
   * @return
   */
  String create(String categoryId, String propertyId);

  /**
   * 根据属性ID删除
   *
   * @param propertyId
   */
  void deleteByPropertyId(String propertyId);

  /**
   * 根据属性ID查询
   *
   * @param propertyId
   * @return
   */
  List<ProductCategoryPropertyRelation> getByPropertyId(String propertyId);

  /**
   * 根据属性ID查询已关联商品分类
   *
   * @param propertyId
   * @return
   */
  List<ProductCategory> getCategoriesByPropertyId(String propertyId);

  /**
   * 根据属性ID批量统计已关联商品分类数量
   *
   * @param propertyIds
   * @return
   */
  List<PropertyCategoryCountDto> countCategoriesByPropertyIds(List<String> propertyIds);

  /**
   * 根据分类ID查询
   *
   * @param categoryId
   * @return
   */
  List<ProductCategoryPropertyRelationDto> getByCategoryId(String categoryId);

  /**
   * 绑定已有属性
   *
   * @param categoryId
   * @param propertyIds
   */
  void bindExistingProperties(String categoryId, List<String> propertyIds);

  /**
   * 创建属性并绑定分类
   *
   * @param categoryId
   * @param vo
   * @return
   */
  String createPropertyAndBind(String categoryId, CreateProductCategoryPropertyDefinitionVo vo);

  /**
   * 根据分类ID和属性ID删除
   *
   * @param categoryId
   * @param propertyId
   */
  void removeByCategoryIdAndPropertyId(String categoryId, String propertyId);
}
