package com.lframework.xingyun.settle.impl;

import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.settle.entity.CustomerSettleSheetDetail;
import com.lframework.xingyun.settle.mappers.CustomerSettleSheetDetailMapper;
import com.lframework.xingyun.settle.service.CustomerSettleSheetDetailService;
import org.springframework.stereotype.Service;

@Service
public class CustomerSettleSheetDetailServiceImpl extends
    BaseMpServiceImpl<CustomerSettleSheetDetailMapper, CustomerSettleSheetDetail>
    implements CustomerSettleSheetDetailService {

}
