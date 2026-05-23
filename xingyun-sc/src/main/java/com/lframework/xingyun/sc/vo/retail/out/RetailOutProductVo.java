package com.lframework.xingyun.sc.vo.retail.out;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class RetailOutProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @Schema(description = "商品ID")
  private String productId;

  /**
   * SKU ID
   */
  @Schema(description = "SKU ID")
  private String skuId;

  /**
   * 原价
   */
  @Schema(description = "原价")
  private BigDecimal oriPrice;

  /**
   * 现价
   */
  @Schema(description = "现价")
  private BigDecimal taxPrice;

  /**
   * 折扣（%）
   */
  @Schema(description = "折扣（%）")
  private BigDecimal discountRate;

  /**
   * 出库数量
   */
  @Schema(description = "出库数量")
  private BigDecimal orderNum;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
