package com.lframework.xingyun.sc.events.order.impl;

import com.lframework.xingyun.core.dto.order.ApprovePassOrderDto;
import com.lframework.xingyun.core.dto.order.ApprovePassOrderDto.OrderType;
import com.lframework.xingyun.sc.events.order.ApprovePassOrderEvent;

public class ApprovePassPurchaseOrderEvent extends ApprovePassOrderEvent {

  public ApprovePassPurchaseOrderEvent(Object source,
      ApprovePassOrderDto order) {
    super(source, order);
    order.setOrderType(OrderType.PURCHASE_ORDER);
  }
}
