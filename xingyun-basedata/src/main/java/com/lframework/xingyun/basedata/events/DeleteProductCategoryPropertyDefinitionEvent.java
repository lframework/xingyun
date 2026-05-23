package com.lframework.xingyun.basedata.events;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyDefinition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteProductCategoryPropertyDefinitionEvent extends DataChangeEvent<ProductCategoryPropertyDefinition> {

  public DeleteProductCategoryPropertyDefinitionEvent(Object source, ProductCategoryPropertyDefinition entity,
      DataChangeType type) {
    super(source, entity, type);
  }
}
