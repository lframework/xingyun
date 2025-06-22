package com.lframework.xingyun.chart.listeners.mq;

import com.lframework.xingyun.chart.enums.OrderChartBizType;
import com.lframework.xingyun.chart.service.OrderChartService;
import com.lframework.xingyun.chart.vo.CreateOrderChartVo;
import com.lframework.starter.web.inner.dto.order.ApprovePassOrderDto;
import com.lframework.xingyun.core.queue.MqStringPool;
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
@Component
public class OrderDataToChartListener {

  @Autowired
  private OrderChartService orderChartService;

  @Transactional(rollbackFor = Exception.class)
  @RabbitListener(bindings = {
      @QueueBinding(value = @Queue(value = "chart.approve_pass_order"), exchange = @Exchange(value = MqStringPool.APPROVE_PASS_ORDER_EXCHANGE, type = ExchangeTypes.FANOUT))})
  public void execute(Message<ApprovePassOrderDto> message) {
    ApprovePassOrderDto event = message.getPayload();
    OrderChartBizType bizType = this.convertBizType(event.getOrderType());
    if (event.getOrderType() == null) {
      log.error("orderType={}，无法匹配业务类型", event.getOrderType());
      return;
    }

    CreateOrderChartVo vo = new CreateOrderChartVo();
    vo.setTotalAmount(event.getTotalAmount());
    vo.setCreateTime(event.getApproveTime());
    vo.setBizType(bizType.getCode());

    orderChartService.create(vo);
  }

  private OrderChartBizType convertBizType(ApprovePassOrderDto.OrderType orderType) {

    if (orderType == ApprovePassOrderDto.OrderType.PURCHASE_ORDER) {
      return OrderChartBizType.PURCHASE_ORDER;
    }
    if (orderType == ApprovePassOrderDto.OrderType.PURCHASE_RETURN) {
      return OrderChartBizType.PURCHASE_RETURN;
    }
    if (orderType == ApprovePassOrderDto.OrderType.SALE_ORDER) {
      return OrderChartBizType.SALE_ORDER;
    }
    if (orderType == ApprovePassOrderDto.OrderType.SALE_RETURN) {
      return OrderChartBizType.SALE_RETURN;
    }
    if (orderType == ApprovePassOrderDto.OrderType.RETAIL_OUT_SHEET) {
      return OrderChartBizType.RETAIL_OUT_SHEET;
    }
    if (orderType == ApprovePassOrderDto.OrderType.RETAIL_RETURN) {
      return OrderChartBizType.RETAIL_RETURN;
    }

    return null;
  }
}
