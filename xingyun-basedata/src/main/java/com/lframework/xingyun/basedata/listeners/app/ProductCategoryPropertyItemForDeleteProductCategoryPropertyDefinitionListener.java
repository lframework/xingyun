package com.lframework.xingyun.basedata.listeners.app;

import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyItem;
import com.lframework.xingyun.basedata.events.DeleteProductCategoryPropertyDefinitionEvent;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductCategoryPropertyItemForDeleteProductCategoryPropertyDefinitionListener implements
    ApplicationListener<DeleteProductCategoryPropertyDefinitionEvent> {

  @Autowired
  private ProductCategoryPropertyItemService ProductCategoryPropertyItemService;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void onApplicationEvent(DeleteProductCategoryPropertyDefinitionEvent event) {

    List<ProductCategoryPropertyItem> ProductCategoryPropertyItemList = ProductCategoryPropertyItemService.getByPropertyId(
        event.getEntity().getId());
    for (ProductCategoryPropertyItem propertyItem : ProductCategoryPropertyItemList) {
      ProductCategoryPropertyItemService.deleteById(propertyItem.getId());
    }
  }
}
