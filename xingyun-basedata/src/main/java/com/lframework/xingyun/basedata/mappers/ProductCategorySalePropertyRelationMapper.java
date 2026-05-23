package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.category.PropertyCategoryCountDto;
import com.lframework.xingyun.basedata.dto.product.category.saleproperty.ProductCategorySalePropertyRelationDto;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import com.lframework.xingyun.basedata.entity.ProductCategorySalePropertyRelation;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductCategorySalePropertyRelationMapper extends BaseMapper<ProductCategorySalePropertyRelation> {

  /**
   * 根据属性ID查询
   *
   * @param propertyId
   * @return
   */
  List<ProductCategorySalePropertyRelation> getByPropertyId(String propertyId);

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
  List<PropertyCategoryCountDto> countCategoriesByPropertyIds(
      @Param("propertyIds") List<String> propertyIds);

  /**
   * 根据分类ID查询
   *
   * @param categoryId
   * @return
   */
  List<ProductCategorySalePropertyRelationDto> getByCategoryId(String categoryId);
}
