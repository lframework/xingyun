package com.lframework.xingyun.settle.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.entity.CustomerSettlePreSheetDetail;
import com.lframework.xingyun.settle.mappers.CustomerSettlePreSheetDetailMapper;
import com.lframework.xingyun.settle.service.ICustomerSettlePreSheetDetailService;
import org.springframework.stereotype.Service;

@Service
public class CustomerSettlePreSheetDetailServiceImpl extends
    BaseMpServiceImpl<CustomerSettlePreSheetDetailMapper, CustomerSettlePreSheetDetail>
    implements ICustomerSettlePreSheetDetailService {

}
