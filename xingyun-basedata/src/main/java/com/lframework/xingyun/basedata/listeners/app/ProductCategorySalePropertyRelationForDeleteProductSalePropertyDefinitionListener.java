package com.lframework.xingyun.basedata.listeners.app;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.xingyun.basedata.entity.ProductCategorySalePropertyRelation;
import com.lframework.xingyun.basedata.events.DeleteProductSalePropertyDefinitionEvent;
import com.lframework.xingyun.basedata.service.product.ProductCategorySalePropertyRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ProductCategorySalePropertyRelationForDeleteProductSalePropertyDefinitionListener implements
    ApplicationListener<DeleteProductSalePropertyDefinitionEvent> {

  @Autowired
  private ProductCategorySalePropertyRelationService ProductCategorySalePropertyRelationService;

  @Override
  public void onApplicationEvent(DeleteProductSalePropertyDefinitionEvent event) {

    Wrapper<ProductCategorySalePropertyRelation> queryWrapper = Wrappers.lambdaQuery(
            ProductCategorySalePropertyRelation.class)
        .eq(ProductCategorySalePropertyRelation::getPropertyId, event.getEntity().getId());

    ProductCategorySalePropertyRelationService.remove(queryWrapper);
  }
}
