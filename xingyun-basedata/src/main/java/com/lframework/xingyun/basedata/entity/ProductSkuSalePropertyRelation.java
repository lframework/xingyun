package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import lombok.Data;

/**
 * SKU销售属性关系
 */
@Data
@TableName("base_data_product_sku_sale_property_relation")
public class ProductSkuSalePropertyRelation extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * SKU ID
   */
  private String skuId;

  /**
   * 销售属性ID
   */
  private String propertyId;

  /**
   * 销售属性值ID
   */
  private String propertyItemId;
}
