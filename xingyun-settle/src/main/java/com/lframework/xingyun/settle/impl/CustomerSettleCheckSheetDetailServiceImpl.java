package com.lframework.xingyun.settle.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.entity.CustomerSettleCheckSheetDetail;
import com.lframework.xingyun.settle.mappers.CustomerSettleCheckSheetDetailMapper;
import com.lframework.xingyun.settle.service.CustomerSettleCheckSheetDetailService;
import org.springframework.stereotype.Service;

@Service
public class CustomerSettleCheckSheetDetailServiceImpl
        extends
        BaseMpServiceImpl<CustomerSettleCheckSheetDetailMapper, CustomerSettleCheckSheetDetail>
        implements CustomerSettleCheckSheetDetailService {

}
