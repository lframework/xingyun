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
 * @since 2021-08-05
 */
@Data
@TableName("base_data_product_property_relation")
public class ProductPropertyRelation extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 商品属性ID
   */
  private String propertyId;

  /**
   * 属性值ID
   */
  private String propertyItemId;

  /**
   * 商品属性值
   */
  private String propertyText;


}
