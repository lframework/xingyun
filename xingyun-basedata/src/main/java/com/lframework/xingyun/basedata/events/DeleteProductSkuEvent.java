package com.lframework.xingyun.basedata.events;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.xingyun.basedata.entity.ProductSku;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteProductSkuEvent extends DataChangeEvent<ProductSku> {

  public DeleteProductSkuEvent(Object source, ProductSku entity,
      DataChangeType type) {
    super(source, entity, type);
  }
}
