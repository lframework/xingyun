package com.lframework.xingyun.core.queue;

import com.lframework.starter.mq.core.queue.QueueDefinition;
import com.lframework.starter.mq.rabbitmq.queue.RabbitMQQueueDefinition;

public class MqConstants {

  /**
   * 增加库存
   */
  public static final QueueDefinition ADD_STOCK = new RabbitMQQueueDefinition(
      MqStringPool.ADD_STOCK_EXCHANGE);

  /**
   * 扣减库存
   */
  public static final QueueDefinition SUB_STOCK = new RabbitMQQueueDefinition(
      MqStringPool.SUB_STOCK_EXCHANGE);

  /**
   * 审核通过订单
   */
  public static final QueueDefinition APPROVE_PASS_ORDER = new RabbitMQQueueDefinition(
      MqStringPool.APPROVE_PASS_ORDER_EXCHANGE);
}
