package com.lframework.xingyun.sc.biz.impl.stock.adjust;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.biz.mappers.StockCostAdjustSheetDetailMapper;
import com.lframework.xingyun.sc.biz.service.stock.adjust.IStockCostAdjustSheetDetailService;
import com.lframework.xingyun.sc.facade.entity.StockCostAdjustSheetDetail;
import org.springframework.stereotype.Service;

@Service
public class StockCostAdjustSheetDetailServiceImpl
    extends BaseMpServiceImpl<StockCostAdjustSheetDetailMapper, StockCostAdjustSheetDetail>
    implements IStockCostAdjustSheetDetailService {

}
