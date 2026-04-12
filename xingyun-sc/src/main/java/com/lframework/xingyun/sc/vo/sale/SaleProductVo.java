package com.lframework.xingyun.sc.vo.sale;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class SaleProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @Schema(description = "商品ID")
  private String productId;

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
   * 销售数量
   */
  @Schema(description = "销售数量")
  private BigDecimal orderNum;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
