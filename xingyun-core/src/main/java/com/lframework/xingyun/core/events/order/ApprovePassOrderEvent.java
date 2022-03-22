package com.lframework.xingyun.core.events.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 业务单据审核通过Event
 */
public abstract class ApprovePassOrderEvent extends ApplicationEvent {

  /**
   * 业务单据ID
   */
  @Getter
  @Setter
  private String id;

  /**
   * 单据总金额
   */
  @Getter
  @Setter
  private BigDecimal totalAmount;

  /**
   * 审核时间
   */
  @Getter
  @Setter
  private LocalDateTime approveTime = LocalDateTime.now();

  /**
   * 单据类型
   */
  @Getter
  private OrderType orderType;

  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with which the event is
   *               associated (never {@code null})
   */
  public ApprovePassOrderEvent(Object source, OrderType orderType) {

    super(source);
    this.orderType = orderType;
  }

  public enum OrderType {
    PURCHASE_ORDER, PURCHASE_RETURN, SALE_ORDER, SALE_RETURN, RETAIL_OUT_SHEET, RETAIL_RETURN
  }
}
