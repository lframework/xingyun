package com.lframework.xingyun.settle.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.entity.CustomerSettleSheetDetail;
import com.lframework.xingyun.settle.mappers.CustomerSettleSheetDetailMapper;
import com.lframework.xingyun.settle.service.ICustomerSettleSheetDetailService;
import org.springframework.stereotype.Service;

@Service
public class CustomerSettleSheetDetailServiceImpl extends
    BaseMpServiceImpl<CustomerSettleSheetDetailMapper, CustomerSettleSheetDetail>
    implements ICustomerSettleSheetDetailService {

}
