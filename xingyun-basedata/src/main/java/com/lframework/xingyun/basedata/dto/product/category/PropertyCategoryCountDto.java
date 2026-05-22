package com.lframework.xingyun.basedata.dto.product.category;

import lombok.Data;

@Data
public class PropertyCategoryCountDto {

  /**
   * 属性ID
   */
  private String propertyId;

  /**
   * 商品分类数量
   */
  private Integer categoryCount;
}
