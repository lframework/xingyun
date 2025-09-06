package com.lframework.xingyun.sc.impl.purchase;

import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.PurchaseOrderDetailBundle;
import com.lframework.xingyun.sc.mappers.PurchaseOrderDetailBundleMapper;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderDetailBundleService;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderDetailBundleServiceImpl extends
    BaseMpServiceImpl<PurchaseOrderDetailBundleMapper, PurchaseOrderDetailBundle>
    implements PurchaseOrderDetailBundleService {

}
