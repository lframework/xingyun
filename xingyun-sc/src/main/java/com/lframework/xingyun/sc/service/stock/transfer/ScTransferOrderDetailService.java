package com.lframework.xingyun.sc.service.stock.transfer;

import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.xingyun.sc.entity.ScTransferOrderDetail;
import java.math.BigDecimal;

public interface ScTransferOrderDetailService extends BaseMpService<ScTransferOrderDetail> {

  /**
   * 收货
   *
   * @param productId
   * @param receiveNum
   * @return
   */
  int receive(String orderId, String productId, BigDecimal receiveNum);

  /**
   * 统计未收货的商品
   *
   * @param orderId
   * @return
   */
  int countUnReceive(String orderId);
}
