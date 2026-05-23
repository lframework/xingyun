package com.lframework.xingyun.basedata.events;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteProductCategoryPropertyItemEvent extends DataChangeEvent<ProductCategoryPropertyItem> {

  public DeleteProductCategoryPropertyItemEvent(Object source, ProductCategoryPropertyItem entity,
      DataChangeType type) {
    super(source, entity, type);
  }
}
