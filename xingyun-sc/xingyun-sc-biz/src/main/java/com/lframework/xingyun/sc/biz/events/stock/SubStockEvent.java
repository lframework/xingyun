package com.lframework.xingyun.sc.biz.events.stock;

import com.lframework.xingyun.sc.facade.dto.stock.ProductStockChangeDto;
import org.springframework.context.ApplicationEvent;

/**
 * 出库事件
 */
public class SubStockEvent extends ApplicationEvent {

  /**
   * 变动记录
   */
  private final ProductStockChangeDto change;

  public SubStockEvent(Object source, ProductStockChangeDto change) {

    super(source);
    this.change = change;
  }

  public ProductStockChangeDto getChange() {

    return change;
  }
}
