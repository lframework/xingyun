package com.lframework.xingyun.sc.biz.impl.stock.take;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.biz.mappers.PreTakeStockSheetDetailMapper;
import com.lframework.xingyun.sc.biz.service.stock.take.IPreTakeStockSheetDetailService;
import com.lframework.xingyun.sc.facade.entity.PreTakeStockSheetDetail;
import org.springframework.stereotype.Service;

@Service
public class IPreTakeStockSheetDetailServiceImpl
    extends BaseMpServiceImpl<PreTakeStockSheetDetailMapper, PreTakeStockSheetDetail>
    implements IPreTakeStockSheetDetailService {

}
