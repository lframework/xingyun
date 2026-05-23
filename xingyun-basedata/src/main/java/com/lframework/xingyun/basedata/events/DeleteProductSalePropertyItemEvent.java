package com.lframework.xingyun.basedata.events;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyDefinition;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteProductSalePropertyItemEvent extends DataChangeEvent<ProductSalePropertyItem> {

  public DeleteProductSalePropertyItemEvent(Object source, ProductSalePropertyItem entity,
      DataChangeType type) {

    super(source, entity, type);
  }
}
