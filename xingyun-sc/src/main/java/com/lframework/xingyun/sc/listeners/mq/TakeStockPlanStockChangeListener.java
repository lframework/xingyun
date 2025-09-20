package com.lframework.xingyun.sc.listeners.mq;

import com.lframework.xingyun.core.queue.MqStringPool;
import com.lframework.xingyun.sc.dto.stock.ProductStockChangeDto;
import com.lframework.xingyun.sc.mappers.TakeStockPlanDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component("takeStockPlanStockChangeListener")
public class TakeStockPlanStockChangeListener {

  @Autowired
  private TakeStockPlanDetailMapper takeStockPlanDetailMapper;

  @Transactional(rollbackFor = Exception.class)
  @RabbitListener(bindings = {
      @QueueBinding(value = @Queue(value = "take_stock_plan.add_stock"), exchange = @Exchange(value = MqStringPool.ADD_STOCK_EXCHANGE, type = ExchangeTypes.FANOUT))
  })
  public void addStock(Message<ProductStockChangeDto> message) {
    ProductStockChangeDto change = message.getPayload();
    log.info("增加库存，统计进项数量 scId = {}, productId = {}, num = {}", change.getScId(),
        change.getProductId(), change.getNum());
    takeStockPlanDetailMapper.addTotalInNum(change.getScId(), change.getProductId(),
        change.getNum().intValue());
  }

  @Transactional(rollbackFor = Exception.class)
  @RabbitListener(bindings = {
      @QueueBinding(value = @Queue(value = "take_stock_plan.sub_stock"), exchange = @Exchange(value = MqStringPool.SUB_STOCK_EXCHANGE, type = ExchangeTypes.FANOUT))
  })
  public void subStock(Message<ProductStockChangeDto> message) {
    ProductStockChangeDto change = message.getPayload();
    log.info("扣减库存，统计出项数量 scId = {}, productId = {}, num = {}", change.getScId(),
        change.getProductId(), change.getNum());
    takeStockPlanDetailMapper.addTotalOutNum(change.getScId(), change.getProductId(),
        change.getNum().intValue());
  }
}
