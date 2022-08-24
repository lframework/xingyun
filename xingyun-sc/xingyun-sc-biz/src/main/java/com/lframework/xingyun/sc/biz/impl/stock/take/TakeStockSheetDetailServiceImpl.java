package com.lframework.xingyun.sc.biz.impl.stock.take;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.biz.mappers.TakeStockSheetDetailMapper;
import com.lframework.xingyun.sc.biz.service.stock.take.ITakeStockSheetDetailService;
import com.lframework.xingyun.sc.facade.entity.TakeStockSheetDetail;
import org.springframework.stereotype.Service;

@Service
public class TakeStockSheetDetailServiceImpl extends
    BaseMpServiceImpl<TakeStockSheetDetailMapper, TakeStockSheetDetail>
    implements ITakeStockSheetDetailService {

}
