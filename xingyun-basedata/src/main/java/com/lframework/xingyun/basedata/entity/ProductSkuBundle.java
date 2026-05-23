package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 组合商品SKU明细
 */
@Data
@TableName("base_data_product_sku_bundle")
public class ProductSkuBundle extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "ProductSkuBundle";

  /**
   * ID
   */
  private String id;

  /**
   * 组合SKU ID
   */
  private String mainSkuId;

  /**
   * 单品SKU ID
   */
  private String skuId;

  /**
   * 包含数量
   */
  private Integer bundleNum;

  /**
   * 采购价
   */
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  private BigDecimal salePrice;

  /**
   * 零售价
   */
  private BigDecimal retailPrice;

  @TableField(fill = FieldFill.INSERT)
  private String createById;

  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateBy;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateById;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
}
