package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.sc.entity.PurchaseOrderDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-09-12
 */
public interface PurchaseOrderDetailMapper extends BaseMapper<PurchaseOrderDetail> {

  /**
   * 根据订单ID查询
   *
   * @param orderId
   * @return
   */
  List<PurchaseOrderDetail> getByOrderId(String orderId);

  /**
   * 增加收货数量
   *
   * @param id
   * @param num
   * @return
   */
  int addReceiveNum(@Param("id") String id, @Param("num") Integer num);

  /**
   * 减少收货数量
   *
   * @param id
   * @param num
   * @return
   */
  int subReceiveNum(@Param("id") String id, @Param("num") Integer num);
}
