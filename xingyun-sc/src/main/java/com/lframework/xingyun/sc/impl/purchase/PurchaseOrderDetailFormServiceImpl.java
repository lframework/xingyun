package com.lframework.xingyun.sc.impl.purchase;

import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.xingyun.sc.entity.PurchaseOrderDetailForm;
import com.lframework.xingyun.sc.mappers.PurchaseOrderDetailFormMapper;
import com.lframework.xingyun.sc.service.purchase.PurchaseOrderDetailFormService;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderDetailFormServiceImpl extends
    BaseMpServiceImpl<PurchaseOrderDetailFormMapper, PurchaseOrderDetailForm>
    implements PurchaseOrderDetailFormService {

}
