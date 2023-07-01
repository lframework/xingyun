package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-07-05
 */
@Data
@TableName("base_data_product_category_property")
public class ProductCategoryProperty extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "ProductCategoryPropertyDto";
  private static final long serialVersionUID = 1L;
  /**
   * ID
   */
  private String id;

  /**
   * 商品属性ID
   */
  private String propertyId;

  /**
   * 商品类目ID
   */
  private String categoryId;
}
