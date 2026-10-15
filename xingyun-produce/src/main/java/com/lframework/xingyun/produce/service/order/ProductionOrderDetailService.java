package com.lframework.xingyun.produce.service.order;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.produce.entity.ProductionOrderDetail;
import java.util.List;

public interface ProductionOrderDetailService extends BaseMpService<ProductionOrderDetail> {

  List<ProductionOrderDetail> listByOrderId(String orderId);

  void deleteByOrderId(String orderId);
}
