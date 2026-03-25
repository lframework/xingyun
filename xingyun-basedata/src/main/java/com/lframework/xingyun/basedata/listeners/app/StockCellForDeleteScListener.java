package com.lframework.xingyun.basedata.listeners.app;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.xingyun.basedata.entity.StockCell;
import com.lframework.xingyun.basedata.events.DeleteStoreCenterEvent;
import com.lframework.xingyun.basedata.service.stockcell.StockCellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StockCellForDeleteScListener implements ApplicationListener<DeleteStoreCenterEvent> {

  @Autowired
  private StockCellService stockCellService;

  @Override
  public void onApplicationEvent(DeleteStoreCenterEvent event) {

    Wrapper<StockCell> queryWrapper = Wrappers.lambdaQuery(StockCell.class)
        .eq(StockCell::getScId, event.getEntity().getId());
    if (stockCellService.count(queryWrapper) > 0) {
      Wrapper<StockCell> deleteWrapper = Wrappers.lambdaUpdate(StockCell.class)
          .set(StockCell::getAvailable, false).eq(StockCell::getScId, event.getEntity().getId());
      stockCellService.update(deleteWrapper);
    }
  }
}
