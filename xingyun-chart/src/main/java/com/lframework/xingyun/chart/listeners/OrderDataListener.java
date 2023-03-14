package com.lframework.xingyun.chart.listeners;

import com.lframework.xingyun.chart.enums.OrderChartBizType;
import com.lframework.xingyun.chart.service.OrderChartService;
import com.lframework.xingyun.chart.vo.CreateOrderChartVo;
import com.lframework.xingyun.core.events.order.ApprovePassOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderDataListener implements ApplicationListener<ApprovePassOrderEvent> {

  @Autowired
  private OrderChartService orderChartService;

  @Override
  public void onApplicationEvent(ApprovePassOrderEvent event) {

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
