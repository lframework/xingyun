package com.lframework.xingyun.chart.facade.mq;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author zmj
 * @since 2022/8/25
 */
@Data
public class ApprovePassOrderEvent {

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
