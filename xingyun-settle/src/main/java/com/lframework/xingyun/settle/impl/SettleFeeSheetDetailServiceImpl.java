package com.lframework.xingyun.settle.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.entity.SettleFeeSheetDetail;
import com.lframework.xingyun.settle.mappers.SettleFeeSheetDetailMapper;
import com.lframework.xingyun.settle.service.ISettleFeeSheetDetailService;
import org.springframework.stereotype.Service;

@Service
public class SettleFeeSheetDetailServiceImpl extends
    BaseMpServiceImpl<SettleFeeSheetDetailMapper, SettleFeeSheetDetail> implements
    ISettleFeeSheetDetailService {

}
