package com.lframework.xingyun.sc.impl.retail;

import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.RetailReturnDetail;
import com.lframework.xingyun.sc.mappers.RetailReturnDetailMapper;
import com.lframework.xingyun.sc.service.retail.RetailReturnDetailService;
import org.springframework.stereotype.Service;

@Service
public class RetailReturnDetailServiceImpl extends
    BaseMpServiceImpl<RetailReturnDetailMapper, RetailReturnDetail>
    implements RetailReturnDetailService {

}
