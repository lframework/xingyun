package com.lframework.xingyun.sc.service.purchase;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.sc.entity.PurchaseRequisitionDetail;
import java.util.List;

public interface PurchaseRequisitionDetailService extends BaseMpService<PurchaseRequisitionDetail> {

  List<PurchaseRequisitionDetail> listByRequisitionId(String requisitionId);

  void deleteByRequisitionId(String requisitionId);
}
