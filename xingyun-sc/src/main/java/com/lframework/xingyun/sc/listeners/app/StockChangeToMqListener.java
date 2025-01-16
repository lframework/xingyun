package com.lframework.xingyun.sc.listeners.app;

import com.lframework.starter.mq.core.producer.MqProducer;
import com.lframework.xingyun.core.queue.MqConstants;
import com.lframework.xingyun.sc.events.stock.AddStockEvent;
import com.lframework.xingyun.sc.events.stock.SubStockEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class StockChangeToMqListener {

  @Autowired
  private MqProducer mqProducer;

  @TransactionalEventListener
  public void addStock(AddStockEvent addStockEvent) {
    mqProducer.sendMessage(MqConstants.ADD_STOCK, addStockEvent.getChange());
  }

  @TransactionalEventListener
  public void subStock(SubStockEvent subStockEvent) {
    mqProducer.sendMessage(MqConstants.SUB_STOCK, subStockEvent.getChange());
  }
}
