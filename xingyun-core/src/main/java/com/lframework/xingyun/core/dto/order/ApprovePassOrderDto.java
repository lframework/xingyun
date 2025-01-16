package com.lframework.xingyun.core.dto.order;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ApprovePassOrderDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务单据ID
   */
  private String id;

  /**
   * 单据总金额
   */
  private BigDecimal totalAmount;

  /**
   * 审核时间
   */
  private LocalDateTime approveTime = LocalDateTime.now();

  /**
   * 单据类型
   */
  private OrderType orderType;

  public enum OrderType {
    PURCHASE_ORDER, PURCHASE_RETURN, SALE_ORDER, SALE_RETURN, RETAIL_OUT_SHEET, RETAIL_RETURN
  }
}
