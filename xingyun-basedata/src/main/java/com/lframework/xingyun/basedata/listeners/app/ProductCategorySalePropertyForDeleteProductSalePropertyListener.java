package com.lframework.xingyun.basedata.listeners.app;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.xingyun.basedata.entity.ProductCategorySaleProperty;
import com.lframework.xingyun.basedata.events.DeleteProductSalePropertyEvent;
import com.lframework.xingyun.basedata.service.product.ProductCategorySalePropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ProductCategorySalePropertyForDeleteProductSalePropertyListener implements
    ApplicationListener<DeleteProductSalePropertyEvent> {

  @Autowired
  private ProductCategorySalePropertyService productCategorySalePropertyService;

  @Override
  public void onApplicationEvent(DeleteProductSalePropertyEvent event) {

    Wrapper<ProductCategorySaleProperty> queryWrapper = Wrappers.lambdaQuery(
            ProductCategorySaleProperty.class)
        .eq(ProductCategorySaleProperty::getPropertyId, event.getEntity().getId());

    productCategorySalePropertyService.remove(queryWrapper);
  }
}
