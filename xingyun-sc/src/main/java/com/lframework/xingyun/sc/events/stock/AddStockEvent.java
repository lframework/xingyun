package com.lframework.xingyun.sc.events.stock;

import com.lframework.xingyun.core.dto.stock.ProductStockChangeDto;
import org.springframework.context.ApplicationEvent;

/**
 * 入库事件
 */
public class AddStockEvent extends ApplicationEvent {

  /**
   * 变动记录
   */
  private ProductStockChangeDto change;

  public AddStockEvent(Object source, ProductStockChangeDto change) {

    super(source);
    this.change = change;
  }

  public ProductStockChangeDto getChange() {

    return change;
  }
}
