package com.lframework.xingyun.basedata.events;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.xingyun.basedata.entity.ProductSaleProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteProductSalePropertyEvent extends DataChangeEvent<ProductSaleProperty> {

  public DeleteProductSalePropertyEvent(Object source, ProductSaleProperty entity,
      DataChangeType type) {

    super(source, entity, type);
  }
}
