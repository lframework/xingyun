package com.lframework.xingyun.basedata.listeners.app;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyValueRelation;
import com.lframework.xingyun.basedata.events.DeleteProductCategoryPropertyDefinitionEvent;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyValueRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductCategoryPropertyValueRelationForDeleteProductCategoryPropertyDefinitionListener implements
    ApplicationListener<DeleteProductCategoryPropertyDefinitionEvent> {

  @Autowired
  private ProductCategoryPropertyValueRelationService ProductCategoryPropertyValueRelationService;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void onApplicationEvent(DeleteProductCategoryPropertyDefinitionEvent event) {

    Wrapper<ProductCategoryPropertyValueRelation> queryWrapper = Wrappers.lambdaQuery(
            ProductCategoryPropertyValueRelation.class)
        .eq(ProductCategoryPropertyValueRelation::getPropertyId, event.getEntity().getId());
    if (ProductCategoryPropertyValueRelationService.count(queryWrapper) > 0) {
      ProductCategoryPropertyValueRelationService.remove(queryWrapper);
    }
  }
}
