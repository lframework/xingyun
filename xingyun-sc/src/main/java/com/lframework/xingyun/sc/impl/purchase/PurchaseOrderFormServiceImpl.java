package com.lframework.xingyun.sc.impl.purchase;

import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.PurchaseOrderForm;
import com.lframework.xingyun.sc.mappers.PurchaseOrderFormMapper;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderFormService;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderFormServiceImpl extends
    BaseMpServiceImpl<PurchaseOrderFormMapper, PurchaseOrderForm> implements
    PurchaseOrderFormService {

}
