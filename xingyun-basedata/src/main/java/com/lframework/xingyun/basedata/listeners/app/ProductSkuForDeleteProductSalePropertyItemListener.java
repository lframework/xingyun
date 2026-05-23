package com.lframework.xingyun.basedata.listeners.app;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.entity.ProductSkuSalePropertyRelation;
import com.lframework.xingyun.basedata.events.DeleteProductSalePropertyItemEvent;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductSkuForDeleteProductSalePropertyItemListener implements
    ApplicationListener<DeleteProductSalePropertyItemEvent> {

  @Autowired
  private ProductSkuService productSkuService;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void onApplicationEvent(DeleteProductSalePropertyItemEvent event) {

    Wrapper<ProductSku> queryWrapper = new MPJLambdaWrapper<ProductSku>()
        .innerJoin(ProductSkuSalePropertyRelation.class, ProductSkuSalePropertyRelation::getSkuId,
            ProductSku::getId)
        .eq(ProductSku::getAvailable, Boolean.TRUE)
        .eq(ProductSkuSalePropertyRelation::getPropertyItemId, event.getEntity().getId());
    if (productSkuService.count(queryWrapper) > 0) {
      throw new DefaultClientException(
          "销售属性值：" + event.getEntity().getName() + "已关联SKU，不允许删除！");
    }
  }
}
