package com.lframework.xingyun.sc.impl.purchase;

import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.PurchaseOrderDetailBundleForm;
import com.lframework.xingyun.sc.mappers.PurchaseOrderDetailBundleFormMapper;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderDetailBundleFormService;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderDetailBundleFormServiceImpl extends
    BaseMpServiceImpl<PurchaseOrderDetailBundleFormMapper, PurchaseOrderDetailBundleForm>
    implements PurchaseOrderDetailBundleFormService {

}
