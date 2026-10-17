package com.lframework.xingyun.sc.vo.purchase;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PurchaseProductVo implements BaseVo, Serializable {

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
   * 修改订单时的原明细ID；空字符串表示新增行，未传时兼容旧客户端。
   */
  @Schema(description = "原采购订单明细ID")
  private String purchaseOrderDetailId;

  /**
   * 页面计算含税价所用税率，用于核对报价快照，不用于覆盖历史税率。
   */
  @Schema(description = "报价税率（%）")
  private BigDecimal taxRate;

  /**
   * 采购价
   */
  @Schema(description = "采购价")
  private BigDecimal purchasePrice;

  /**
   * 采购数量
   */
  @Schema(description = "采购数量")
  private BigDecimal purchaseNum;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
