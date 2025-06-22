package com.lframework.xingyun.sc.impl.sale;

import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.SaleOrderDetailBundle;
import com.lframework.xingyun.sc.mappers.SaleOrderDetailBundleMapper;
import com.lframework.xingyun.sc.service.sale.SaleOrderDetailBundleService;
import org.springframework.stereotype.Service;

@Service
public class SaleOrderDetailBundleServiceImpl extends
    BaseMpServiceImpl<SaleOrderDetailBundleMapper, SaleOrderDetailBundle>
    implements SaleOrderDetailBundleService {

}
