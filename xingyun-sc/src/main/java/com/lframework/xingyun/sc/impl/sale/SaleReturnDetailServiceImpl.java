package com.lframework.xingyun.sc.impl.sale;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.SaleReturnDetail;
import com.lframework.xingyun.sc.mappers.SaleReturnDetailMapper;
import com.lframework.xingyun.sc.service.sale.ISaleReturnDetailService;
import org.springframework.stereotype.Service;

@Service
public class SaleReturnDetailServiceImpl extends
    BaseMpServiceImpl<SaleReturnDetailMapper, SaleReturnDetail> implements
    ISaleReturnDetailService {

}
