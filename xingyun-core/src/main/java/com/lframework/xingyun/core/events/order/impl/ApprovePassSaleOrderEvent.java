package com.lframework.xingyun.core.events.order.impl;

import com.lframework.xingyun.core.events.order.ApprovePassOrderEvent;

public class ApprovePassSaleOrderEvent extends ApprovePassOrderEvent {

    /**
     * Create a new {@code ApplicationEvent}.
     * @param source the object on which the event initially occurred or with
     * which the event is associated (never {@code null})
     */
    public ApprovePassSaleOrderEvent(Object source) {

        super(source, OrderType.SALE_ORDER);
    }
}
