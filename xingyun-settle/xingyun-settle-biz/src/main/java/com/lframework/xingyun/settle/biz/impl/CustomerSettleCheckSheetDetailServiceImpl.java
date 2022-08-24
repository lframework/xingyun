package com.lframework.xingyun.settle.biz.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.biz.mappers.CustomerSettleCheckSheetDetailMapper;
import com.lframework.xingyun.settle.biz.service.ICustomerSettleCheckSheetDetailService;
import com.lframework.xingyun.settle.facade.entity.CustomerSettleCheckSheetDetail;
import org.springframework.stereotype.Service;

@Service
public class CustomerSettleCheckSheetDetailServiceImpl
        extends
        BaseMpServiceImpl<CustomerSettleCheckSheetDetailMapper, CustomerSettleCheckSheetDetail>
        implements ICustomerSettleCheckSheetDetailService {

}
