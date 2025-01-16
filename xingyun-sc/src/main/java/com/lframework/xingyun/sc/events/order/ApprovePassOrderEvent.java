package com.lframework.xingyun.sc.events.order;

import com.lframework.xingyun.core.dto.order.ApprovePassOrderDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 业务单据审核通过Event
 */
public abstract class ApprovePassOrderEvent extends ApplicationEvent {

  @Getter
  private ApprovePassOrderDto order;

  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with which the event is
   *               associated (never {@code null})
   */
  public ApprovePassOrderEvent(Object source, ApprovePassOrderDto order) {

    super(source);
    this.order = order;
  }
}
