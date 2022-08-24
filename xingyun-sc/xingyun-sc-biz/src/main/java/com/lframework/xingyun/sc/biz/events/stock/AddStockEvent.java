package com.lframework.xingyun.sc.biz.events.stock;

import com.lframework.xingyun.sc.facade.dto.stock.ProductStockChangeDto;
import org.springframework.context.ApplicationEvent;

/**
 * 入库事件
 */
public class AddStockEvent extends ApplicationEvent {

  /**
   * 变动记录
   */
  private final ProductStockChangeDto change;

  public AddStockEvent(Object source, ProductStockChangeDto change) {

    super(source);
    this.change = change;
  }

  public ProductStockChangeDto getChange() {

    return change;
  }
}
