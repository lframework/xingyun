package com.lframework.xingyun.core.events.stock;

import com.lframework.xingyun.core.dto.stock.ProductStockChangeDto;
import org.springframework.context.ApplicationEvent;

/**
 * 出库事件
 */
public class SubStockEvent extends ApplicationEvent {

  /**
   * 变动记录
   */
  private ProductStockChangeDto change;

  public SubStockEvent(Object source, ProductStockChangeDto change) {

    super(source);
    this.change = change;
  }

  public ProductStockChangeDto getChange() {

    return change;
  }
}
