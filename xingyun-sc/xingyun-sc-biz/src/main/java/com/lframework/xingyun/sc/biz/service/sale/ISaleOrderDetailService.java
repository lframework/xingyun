package com.lframework.xingyun.sc.biz.service.sale;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.facade.entity.SaleOrderDetail;
import java.util.List;

public interface ISaleOrderDetailService extends BaseMpService<SaleOrderDetail> {

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
  void addOutNum(String id, Integer num);

  /**
   * 减少出库数量
   *
   * @param id
   * @param num
   */
  void subOutNum(String id, Integer num);
}
