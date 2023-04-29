package com.lframework.xingyun.sc.impl.stock.adjust;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.StockAdjustSheetDetail;
import com.lframework.xingyun.sc.mappers.StockAdjustSheetDetailMapper;
import com.lframework.xingyun.sc.service.stock.adjust.StockAdjustSheetDetailService;
import org.springframework.stereotype.Service;

@Service
public class StockAdjustSheetDetailServiceImpl
    extends BaseMpServiceImpl<StockAdjustSheetDetailMapper, StockAdjustSheetDetail>
    implements StockAdjustSheetDetailService {

}
