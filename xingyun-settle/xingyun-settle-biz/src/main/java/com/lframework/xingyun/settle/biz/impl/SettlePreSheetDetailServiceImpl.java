package com.lframework.xingyun.settle.biz.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.biz.mappers.SettlePreSheetDetailMapper;
import com.lframework.xingyun.settle.biz.service.ISettlePreSheetDetailService;
import com.lframework.xingyun.settle.facade.entity.SettlePreSheetDetail;
import org.springframework.stereotype.Service;

@Service
public class SettlePreSheetDetailServiceImpl extends BaseMpServiceImpl<SettlePreSheetDetailMapper, SettlePreSheetDetail>
        implements ISettlePreSheetDetailService {

}
