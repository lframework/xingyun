package com.lframework.xingyun.core.queue;

public interface MqStringPool {

  // 增加库存
  String ADD_STOCK_EXCHANGE = "add_stock.fanout";

  // 扣减库存
  String SUB_STOCK_EXCHANGE = "sub_stock.fanout";

  // 审核通过订单
  String APPROVE_PASS_ORDER_EXCHANGE = "approve_pass_order.fanout";
}
