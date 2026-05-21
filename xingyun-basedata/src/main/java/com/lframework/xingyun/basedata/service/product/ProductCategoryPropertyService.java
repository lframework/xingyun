package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.basedata.dto.product.category.property.ProductCategoryPropertyDto;
import com.lframework.xingyun.basedata.entity.ProductCategoryProperty;
import com.lframework.xingyun.basedata.vo.product.property.CreateProductPropertyVo;
import java.util.List;

public interface ProductCategoryPropertyService extends BaseMpService<ProductCategoryProperty> {

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
  List<ProductCategoryProperty> getByPropertyId(String propertyId);

  /**
   * 根据分类ID查询
   *
   * @param categoryId
   * @return
   */
  List<ProductCategoryPropertyDto> getByCategoryId(String categoryId);

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
  String createPropertyAndBind(String categoryId, CreateProductPropertyVo vo);

  /**
   * 根据分类ID和属性ID删除
   *
   * @param categoryId
   * @param propertyId
   */
  void removeByCategoryIdAndPropertyId(String categoryId, String propertyId);
}
