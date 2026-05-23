package com.lframework.xingyun.basedata.listeners.app;

import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.events.DeleteProductEvent;
import com.lframework.xingyun.basedata.service.product.ProductSkuCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductCodeForDeleteProductListener implements
    ApplicationListener<DeleteProductEvent> {

  @Autowired
  private ProductSkuCodeService productSkuCodeService;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void onApplicationEvent(DeleteProductEvent event) {
    Product product = event.getEntity();

    productSkuCodeService.removeByProductId(product.getId());
  }
}
