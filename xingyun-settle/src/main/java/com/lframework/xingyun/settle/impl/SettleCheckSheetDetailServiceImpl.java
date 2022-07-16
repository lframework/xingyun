package com.lframework.xingyun.settle.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.entity.SettleCheckSheetDetail;
import com.lframework.xingyun.settle.mappers.SettleCheckSheetDetailMapper;
import com.lframework.xingyun.settle.service.ISettleCheckSheetDetailService;
import org.springframework.stereotype.Service;

@Service
public class SettleCheckSheetDetailServiceImpl
    extends BaseMpServiceImpl<SettleCheckSheetDetailMapper, SettleCheckSheetDetail>
    implements ISettleCheckSheetDetailService {

}
