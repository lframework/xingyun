package com.lframework.xingyun.basedata.listeners.app;

import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.events.DeleteProductSkuEvent;
import com.lframework.xingyun.basedata.service.product.ProductSkuCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductCodeForDeleteProductSkuListener implements
    ApplicationListener<DeleteProductSkuEvent> {

  @Autowired
  private ProductSkuCodeService productSkuCodeService;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void onApplicationEvent(DeleteProductSkuEvent event) {
    ProductSku product = event.getEntity();

    productSkuCodeService.removeBySkuId(product.getId());
  }
}
