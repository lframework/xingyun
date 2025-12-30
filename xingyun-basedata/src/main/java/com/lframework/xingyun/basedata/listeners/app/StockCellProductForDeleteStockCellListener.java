package com.lframework.xingyun.basedata.listeners.app;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.xingyun.basedata.entity.StockCellProduct;
import com.lframework.xingyun.basedata.events.DeleteStockCellEvent;
import com.lframework.xingyun.basedata.service.stockcell.StockCellProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StockCellProductForDeleteStockCellListener implements
    ApplicationListener<DeleteStockCellEvent> {

  @Autowired
  private StockCellProductService stockCellProductService;

  @Override
  public void onApplicationEvent(DeleteStockCellEvent event) {

    Wrapper<StockCellProduct> queryWrapper = Wrappers.lambdaQuery(StockCellProduct.class)
        .eq(StockCellProduct::getStockCellId, event.getEntity().getId());
    if (stockCellProductService.count(queryWrapper) > 0) {
      throw new DefaultClientException(
          "仓位：" + event.getEntity().getName() + "已关联商品，不允许删除！");
    }
  }
}
