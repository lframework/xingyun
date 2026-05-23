package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;

/**
 * SKU零售价
 */
@Data
@TableName("base_data_product_sku_retail")
public class ProductSkuRetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * SKU ID
   */
  private String id;

  /**
   * 零售价
   */
  private BigDecimal price;
}
