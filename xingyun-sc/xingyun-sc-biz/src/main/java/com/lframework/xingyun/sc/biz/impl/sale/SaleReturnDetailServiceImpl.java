package com.lframework.xingyun.sc.biz.impl.sale;

import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.biz.mappers.SaleReturnDetailMapper;
import com.lframework.xingyun.sc.biz.service.sale.ISaleReturnDetailService;
import com.lframework.xingyun.sc.facade.entity.SaleReturnDetail;
import org.springframework.stereotype.Service;

@Service
public class SaleReturnDetailServiceImpl extends
    BaseMpServiceImpl<SaleReturnDetailMapper, SaleReturnDetail>
    implements ISaleReturnDetailService {

}
