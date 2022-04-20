package com.lframework.xingyun.core.events.stock.take;

import org.springframework.context.ApplicationEvent;

/**
 * 删除盘点任务事件
 */
public class DeleteTakeStockPlanEvent extends ApplicationEvent {

  /**
   * 盘点任务ID
   */
  private String id;

  public DeleteTakeStockPlanEvent(Object source, String id) {

    super(source);

    this.id = id;
  }

  public String getId() {

    return id;
  }
}
