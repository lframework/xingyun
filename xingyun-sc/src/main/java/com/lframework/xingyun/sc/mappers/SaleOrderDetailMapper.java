package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.sc.entity.SaleOrderDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-10-21
 */
public interface SaleOrderDetailMapper extends BaseMapper<SaleOrderDetail> {

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
   * @return
   */
  int addOutNum(@Param("id") String id, @Param("num") Integer num);

  /**
   * 减少出库数量
   *
   * @param id
   * @param num
   * @return
   */
  int subOutNum(@Param("id") String id, @Param("num") Integer num);
}
