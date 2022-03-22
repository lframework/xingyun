package com.lframework.xingyun.basedata.dto.product.category.property;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class ProductCategoryPropertyDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "ProductCategoryPropertyDto";

  /**
   * ID
   */
  private String id;

  /**
   * 商品类目ID
   */
  private String categoryId;

  /**
   * 商品属性ID
   */
  private String propertyId;
}
