package com.lframework.xingyun.basedata.events;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.xingyun.basedata.entity.StockCell;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteStockCellEvent extends DataChangeEvent<StockCell> {

  public DeleteStockCellEvent(Object source, StockCell entity,
      DataChangeType type) {
    super(source, entity, type);
  }
}
