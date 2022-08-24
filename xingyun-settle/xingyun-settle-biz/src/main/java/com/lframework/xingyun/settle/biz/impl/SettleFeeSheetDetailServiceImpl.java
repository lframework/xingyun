package com.lframework.xingyun.settle.biz.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.biz.mappers.SettleFeeSheetDetailMapper;
import com.lframework.xingyun.settle.biz.service.ISettleFeeSheetDetailService;
import com.lframework.xingyun.settle.facade.entity.SettleFeeSheetDetail;
import org.springframework.stereotype.Service;

@Service
public class SettleFeeSheetDetailServiceImpl extends BaseMpServiceImpl<SettleFeeSheetDetailMapper, SettleFeeSheetDetail>
        implements ISettleFeeSheetDetailService {

}
