package com.lframework.xingyun.sc.events.order.impl;

import com.lframework.starter.web.inner.dto.order.ApprovePassOrderDto;
import com.lframework.starter.web.inner.dto.order.ApprovePassOrderDto.OrderType;
import com.lframework.xingyun.sc.events.order.ApprovePassOrderEvent;

public class ApprovePassSaleOrderEvent extends ApprovePassOrderEvent {

  public ApprovePassSaleOrderEvent(Object source,
      ApprovePassOrderDto order) {
    super(source, order);
    order.setOrderType(OrderType.SALE_ORDER);
  }
}
