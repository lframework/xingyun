package com.lframework.xingyun.basedata.listeners.app;

import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.events.DeleteProductSkuEvent;
import com.lframework.xingyun.basedata.service.product.ProductPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductPurchaseForDeleteProductSkuListener implements
    ApplicationListener<DeleteProductSkuEvent> {

  @Autowired
  private ProductPurchaseService productPurchaseService;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void onApplicationEvent(DeleteProductSkuEvent event) {
    ProductSku sku = event.getEntity();

    productPurchaseService.removeById(sku.getId());
  }
}
