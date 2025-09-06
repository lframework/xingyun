package com.lframework.xingyun.sc.events.stock;

import com.lframework.xingyun.sc.dto.stock.ProductStockChangeDto;
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
