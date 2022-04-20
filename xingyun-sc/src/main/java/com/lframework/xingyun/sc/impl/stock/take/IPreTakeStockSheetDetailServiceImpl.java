package com.lframework.xingyun.sc.impl.stock.take;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.PreTakeStockSheetDetail;
import com.lframework.xingyun.sc.mappers.PreTakeStockSheetDetailMapper;
import com.lframework.xingyun.sc.service.stock.take.IPreTakeStockSheetDetailService;
import org.springframework.stereotype.Service;

@Service
public class IPreTakeStockSheetDetailServiceImpl
    extends BaseMpServiceImpl<PreTakeStockSheetDetailMapper, PreTakeStockSheetDetail>
    implements IPreTakeStockSheetDetailService {

}
