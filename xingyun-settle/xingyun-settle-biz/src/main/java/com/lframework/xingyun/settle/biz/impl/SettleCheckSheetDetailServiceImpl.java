package com.lframework.xingyun.settle.biz.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.biz.mappers.SettleCheckSheetDetailMapper;
import com.lframework.xingyun.settle.biz.service.ISettleCheckSheetDetailService;
import com.lframework.xingyun.settle.facade.entity.SettleCheckSheetDetail;
import org.springframework.stereotype.Service;

@Service
public class SettleCheckSheetDetailServiceImpl
    extends BaseMpServiceImpl<SettleCheckSheetDetailMapper, SettleCheckSheetDetail>
    implements ISettleCheckSheetDetailService {

}
