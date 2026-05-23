package com.lframework.xingyun.basedata.listeners.app;

import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.events.DeleteProductSkuEvent;
import com.lframework.xingyun.basedata.service.product.ProductRetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductRetailForDeleteProductSkuListener implements
    ApplicationListener<DeleteProductSkuEvent> {

  @Autowired
  private ProductRetailService productRetailService;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void onApplicationEvent(DeleteProductSkuEvent event) {
    ProductSku sku = event.getEntity();

    productRetailService.removeById(sku.getId());
  }
}
