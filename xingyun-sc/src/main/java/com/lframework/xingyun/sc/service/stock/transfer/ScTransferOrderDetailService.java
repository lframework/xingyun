package com.lframework.xingyun.sc.service.stock.transfer;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.entity.ScTransferOrderDetail;

public interface ScTransferOrderDetailService extends BaseMpService<ScTransferOrderDetail> {

  /**
   * 收货
   *
   * @param productId
   * @param receiveNum
   * @return
   */
  int receive(String orderId, String productId, Integer receiveNum);

  /**
   * 统计未收货的商品
   *
   * @param orderId
   * @return
   */
  int countUnReceive(String orderId);
}
