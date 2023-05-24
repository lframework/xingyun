package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.entity.ScTransferOrderDetail;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 仓库调拨单明细 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface ScTransferOrderDetailMapper extends BaseMapper<ScTransferOrderDetail> {

  /**
   * 收货
   *
   * @param productId
   * @param receiveNum
   * @return
   */
  int receive(@Param("orderId") String orderId, @Param("productId") String productId,
      @Param("receiveNum") Integer receiveNum);

  /**
   * 统计未收货的商品
   *
   * @param orderId
   * @return
   */
  int countUnReceive(@Param("orderId") String orderId);
}
