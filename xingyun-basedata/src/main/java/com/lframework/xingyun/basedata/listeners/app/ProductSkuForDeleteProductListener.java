package com.lframework.xingyun.basedata.listeners.app;

import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.events.DeleteProductEvent;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductSkuForDeleteProductListener implements
    ApplicationListener<DeleteProductEvent> {

  @Autowired
  private ProductSkuService productSkuService;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void onApplicationEvent(DeleteProductEvent event) {
    Product product = event.getEntity();

    productSkuService.deleteByProductId(product.getId());
  }
}
