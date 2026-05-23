package com.lframework.xingyun.sc.vo.purchase.returned;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ReturnProductVo implements BaseVo, Serializable {

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
   * 退货价
   */
  @Schema(description = "退货价")
  private BigDecimal purchasePrice;

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
   * 收货单明细ID
   */
  @Schema(description = "收货单明细ID")
  private String receiveSheetDetailId;
}
