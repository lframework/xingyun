package com.lframework.xingyun.sc.impl.stock.adjust;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.StockCostAdjustSheetDetail;
import com.lframework.xingyun.sc.mappers.StockCostAdjustSheetDetailMapper;
import com.lframework.xingyun.sc.service.stock.adjust.IStockCostAdjustSheetDetailService;
import org.springframework.stereotype.Service;

@Service
public class StockCostAdjustSheetDetailServiceImpl
    extends BaseMpServiceImpl<StockCostAdjustSheetDetailMapper, StockCostAdjustSheetDetail>
    implements IStockCostAdjustSheetDetailService {

}
