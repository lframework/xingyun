package com.lframework.xingyun.sc.listeners.app;

import com.lframework.starter.mq.core.producer.MqProducer;
import com.lframework.xingyun.sc.events.order.ApprovePassOrderEvent;
import com.lframework.xingyun.core.queue.MqConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderDataListener {

  @Autowired
  private MqProducer mqProducer;

  @TransactionalEventListener
  public void execute(ApprovePassOrderEvent event) {
    mqProducer.sendMessage(MqConstants.APPROVE_PASS_ORDER, event.getOrder());
  }
}
