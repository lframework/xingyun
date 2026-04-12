package com.lframework.xingyun.sc.vo.sale.returned;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class SaleReturnProductVo implements BaseVo, Serializable {

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
   * 退货数量
   */
  @Schema(description = "退货数量")
  private BigDecimal returnNum;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 出库单明细ID
   */
  @Schema(description = "出库单明细ID")
  private String outSheetDetailId;
}
