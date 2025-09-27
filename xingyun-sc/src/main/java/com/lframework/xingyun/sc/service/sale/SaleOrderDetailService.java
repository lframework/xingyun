package com.lframework.xingyun.sc.service.sale;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.sc.entity.SaleOrderDetail;
import java.math.BigDecimal;
import java.util.List;

public interface SaleOrderDetailService extends BaseMpService<SaleOrderDetail> {

  /**
   * 根据订单ID查询
   *
   * @param orderId
   * @return
   */
  List<SaleOrderDetail> getByOrderId(String orderId);

  /**
   * 增加出库数量
   *
   * @param id
   * @param num
   */
  void addOutNum(String id, BigDecimal num);

  /**
   * 减少出库数量
   *
   * @param id
   * @param num
   */
  void subOutNum(String id, BigDecimal num);
}
