package com.lframework.xingyun.sc.events.order.impl;

import com.lframework.starter.web.inner.dto.order.ApprovePassOrderDto;
import com.lframework.starter.web.inner.dto.order.ApprovePassOrderDto.OrderType;
import com.lframework.xingyun.sc.events.order.ApprovePassOrderEvent;

public class ApprovePassSaleReturnEvent extends ApprovePassOrderEvent {

  public ApprovePassSaleReturnEvent(Object source,
      ApprovePassOrderDto order) {
    super(source, order);
    order.setOrderType(OrderType.SALE_RETURN);
  }
}
