package com.lframework.xingyun.settle.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.entity.SettleSheetDetail;
import com.lframework.xingyun.settle.mappers.SettleSheetDetailMapper;
import com.lframework.xingyun.settle.service.ISettleSheetDetailService;
import org.springframework.stereotype.Service;

@Service
public class SettleSheetDetailServiceImpl extends
    BaseMpServiceImpl<SettleSheetDetailMapper, SettleSheetDetail> implements
    ISettleSheetDetailService {

}
