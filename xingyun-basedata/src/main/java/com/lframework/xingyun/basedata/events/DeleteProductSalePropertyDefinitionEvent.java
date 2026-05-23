package com.lframework.xingyun.basedata.events;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.xingyun.basedata.entity.ProductSalePropertyDefinition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteProductSalePropertyDefinitionEvent extends DataChangeEvent<ProductSalePropertyDefinition> {

  public DeleteProductSalePropertyDefinitionEvent(Object source, ProductSalePropertyDefinition entity,
      DataChangeType type) {

    super(source, entity, type);
  }
}
