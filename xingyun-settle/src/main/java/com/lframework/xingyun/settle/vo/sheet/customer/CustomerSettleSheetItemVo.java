package com.lframework.xingyun.settle.vo.sheet.customer;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CustomerSettleSheetItemVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 单据ID
   */
  @Schema(description = "单据ID")
  private String id;

  /**
   * 实收金额
   */
  @Schema(description = "实收金额")
  private BigDecimal payAmount;

  /**
   * 优惠金额
   */
  @Schema(description = "优惠金额")
  private BigDecimal discountAmount;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
