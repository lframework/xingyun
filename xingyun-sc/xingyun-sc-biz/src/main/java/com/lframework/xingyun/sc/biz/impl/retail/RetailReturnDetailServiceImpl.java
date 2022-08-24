package com.lframework.xingyun.sc.biz.impl.retail;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.biz.mappers.RetailReturnDetailMapper;
import com.lframework.xingyun.sc.biz.service.retail.IRetailReturnDetailService;
import com.lframework.xingyun.sc.facade.entity.RetailReturnDetail;
import org.springframework.stereotype.Service;

@Service
public class RetailReturnDetailServiceImpl extends
    BaseMpServiceImpl<RetailReturnDetailMapper, RetailReturnDetail>
    implements IRetailReturnDetailService {

}
