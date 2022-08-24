package com.lframework.xingyun.settle.biz.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.biz.mappers.CustomerSettleFeeSheetDetailMapper;
import com.lframework.xingyun.settle.biz.service.ICustomerSettleFeeSheetDetailService;
import com.lframework.xingyun.settle.facade.entity.CustomerSettleFeeSheetDetail;
import org.springframework.stereotype.Service;

@Service
public class CustomerSettleFeeSheetDetailServiceImpl extends
    BaseMpServiceImpl<CustomerSettleFeeSheetDetailMapper, CustomerSettleFeeSheetDetail>
    implements ICustomerSettleFeeSheetDetailService {

}
