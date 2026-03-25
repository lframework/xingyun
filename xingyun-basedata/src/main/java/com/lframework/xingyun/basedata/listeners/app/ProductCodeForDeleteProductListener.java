package com.lframework.xingyun.basedata.listeners.app;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductCode;
import com.lframework.xingyun.basedata.events.DeleteProductEvent;
import com.lframework.xingyun.basedata.service.product.ProductCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductCodeForDeleteProductListener implements
    ApplicationListener<DeleteProductEvent> {

  @Autowired
  private ProductCodeService productCodeService;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void onApplicationEvent(DeleteProductEvent event) {
    Product product = event.getEntity();

    Wrapper<ProductCode> deleteWrapper = Wrappers.lambdaQuery(ProductCode.class)
        .eq(ProductCode::getProductId, product.getId());
    productCodeService.remove(deleteWrapper);
  }
}
