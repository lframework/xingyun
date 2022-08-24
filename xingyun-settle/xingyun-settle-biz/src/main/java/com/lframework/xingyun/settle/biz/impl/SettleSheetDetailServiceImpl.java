package com.lframework.xingyun.settle.biz.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.biz.mappers.SettleSheetDetailMapper;
import com.lframework.xingyun.settle.biz.service.ISettleSheetDetailService;
import com.lframework.xingyun.settle.facade.entity.SettleSheetDetail;
import org.springframework.stereotype.Service;

@Service
public class SettleSheetDetailServiceImpl extends BaseMpServiceImpl<SettleSheetDetailMapper, SettleSheetDetail>
        implements ISettleSheetDetailService {

}
