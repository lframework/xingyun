package com.lframework.xingyun.settle.impl;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.entity.CustomerSettleFeeSheetDetail;
import com.lframework.xingyun.settle.mappers.CustomerSettleFeeSheetDetailMapper;
import com.lframework.xingyun.settle.service.CustomerSettleFeeSheetDetailService;
import org.springframework.stereotype.Service;

@Service
public class CustomerSettleFeeSheetDetailServiceImpl extends
    BaseMpServiceImpl<CustomerSettleFeeSheetDetailMapper, CustomerSettleFeeSheetDetail>
    implements CustomerSettleFeeSheetDetailService {

}
