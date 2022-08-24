package com.lframework.xingyun.settle.biz.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.biz.mappers.CustomerSettleSheetDetailMapper;
import com.lframework.xingyun.settle.biz.service.ICustomerSettleSheetDetailService;
import com.lframework.xingyun.settle.facade.entity.CustomerSettleSheetDetail;
import org.springframework.stereotype.Service;

@Service
public class CustomerSettleSheetDetailServiceImpl extends
    BaseMpServiceImpl<CustomerSettleSheetDetailMapper, CustomerSettleSheetDetail>
    implements ICustomerSettleSheetDetailService {

}
