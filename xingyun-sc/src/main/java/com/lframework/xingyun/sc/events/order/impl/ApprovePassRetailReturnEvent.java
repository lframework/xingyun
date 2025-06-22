package com.lframework.xingyun.sc.events.order.impl;

import com.lframework.starter.web.inner.dto.order.ApprovePassOrderDto;
import com.lframework.starter.web.inner.dto.order.ApprovePassOrderDto.OrderType;
import com.lframework.xingyun.sc.events.order.ApprovePassOrderEvent;

public class ApprovePassRetailReturnEvent extends ApprovePassOrderEvent {

  public ApprovePassRetailReturnEvent(Object source,
      ApprovePassOrderDto order) {
    super(source, order);
    order.setOrderType(OrderType.RETAIL_RETURN);
  }
}
