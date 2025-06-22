package com.lframework.xingyun.sc.impl.stock.take;

import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.TakeStockSheetDetail;
import com.lframework.xingyun.sc.mappers.TakeStockSheetDetailMapper;
import com.lframework.xingyun.sc.service.stock.take.TakeStockSheetDetailService;
import org.springframework.stereotype.Service;

@Service
public class TakeStockSheetDetailServiceImpl extends
    BaseMpServiceImpl<TakeStockSheetDetailMapper, TakeStockSheetDetail>
    implements TakeStockSheetDetailService {

}
