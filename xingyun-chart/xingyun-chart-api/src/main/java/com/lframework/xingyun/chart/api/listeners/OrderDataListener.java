package com.lframework.xingyun.chart.api.listeners;

import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.chart.biz.service.IOrderChartService;
import com.lframework.xingyun.chart.facade.constants.MqConstants;
import com.lframework.xingyun.chart.facade.enums.OrderChartBizType;
import com.lframework.xingyun.chart.facade.mq.ApprovePassOrderEvent;
import com.lframework.xingyun.chart.facade.vo.CreateOrderChartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderDataListener {

  @Autowired
  private IOrderChartService orderChartService;

  @JmsListener(destination = MqConstants.QUEUE_ORDER_CHART)
  public void excute(String obj) {
    ApprovePassOrderEvent event = JsonUtil.parseObject(obj, ApprovePassOrderEvent.class);

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

  private OrderChartBizType convertBizType(ApprovePassOrderEvent.OrderType orderType) {

    if (orderType == ApprovePassOrderEvent.OrderType.PURCHASE_ORDER) {
      return OrderChartBizType.PURCHASE_ORDER;
    }
    if (orderType == ApprovePassOrderEvent.OrderType.PURCHASE_RETURN) {
      return OrderChartBizType.PURCHASE_RETURN;
    }
    if (orderType == ApprovePassOrderEvent.OrderType.SALE_ORDER) {
      return OrderChartBizType.SALE_ORDER;
    }
    if (orderType == ApprovePassOrderEvent.OrderType.SALE_RETURN) {
      return OrderChartBizType.SALE_RETURN;
    }
    if (orderType == ApprovePassOrderEvent.OrderType.RETAIL_OUT_SHEET) {
      return OrderChartBizType.RETAIL_OUT_SHEET;
    }
    if (orderType == ApprovePassOrderEvent.OrderType.RETAIL_RETURN) {
      return OrderChartBizType.RETAIL_RETURN;
    }

    return null;
  }
}
