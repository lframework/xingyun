package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.entity.ProductCategoryProperty;
import java.util.List;

public interface IProductCategoryPropertyService extends BaseMpService<ProductCategoryProperty> {

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
}
