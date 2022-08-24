package com.lframework.xingyun.settle.biz.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.biz.mappers.CustomerSettlePreSheetDetailMapper;
import com.lframework.xingyun.settle.biz.service.ICustomerSettlePreSheetDetailService;
import com.lframework.xingyun.settle.facade.entity.CustomerSettlePreSheetDetail;
import org.springframework.stereotype.Service;

@Service
public class CustomerSettlePreSheetDetailServiceImpl extends
    BaseMpServiceImpl<CustomerSettlePreSheetDetailMapper, CustomerSettlePreSheetDetail>
    implements ICustomerSettlePreSheetDetailService {

}
