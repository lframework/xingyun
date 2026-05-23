package com.lframework.xingyun.basedata.listeners.app;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyRelation;
import com.lframework.xingyun.basedata.events.DeleteProductCategoryPropertyDefinitionEvent;
import com.lframework.xingyun.basedata.service.product.ProductCategoryPropertyRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductCategoryPropertyRelationForDeleteProductCategoryPropertyDefinitionListener implements
    ApplicationListener<DeleteProductCategoryPropertyDefinitionEvent> {

  @Autowired
  private ProductCategoryPropertyRelationService ProductCategoryPropertyRelationService;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void onApplicationEvent(DeleteProductCategoryPropertyDefinitionEvent event) {

    Wrapper<ProductCategoryPropertyRelation> queryWrapper = Wrappers.lambdaQuery(
            ProductCategoryPropertyRelation.class)
        .eq(ProductCategoryPropertyRelation::getPropertyId, event.getEntity().getId());
    if (ProductCategoryPropertyRelationService.count(queryWrapper) > 0) {
      ProductCategoryPropertyRelationService.remove(queryWrapper);
    }
  }
}
