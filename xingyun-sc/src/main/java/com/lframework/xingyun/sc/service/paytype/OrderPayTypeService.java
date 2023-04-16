package com.lframework.xingyun.sc.service.paytype;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.entity.OrderPayType;
import com.lframework.xingyun.sc.vo.paytype.OrderPayTypeVo;
import java.util.List;

public interface OrderPayTypeService extends BaseMpService<OrderPayType> {

  /**
   * 创建
   *
   * @param orderId
   * @param data
   */
  void create(String orderId, List<OrderPayTypeVo> data);

  /**
   * 根据订单ID删除
   *
   * @param orderId
   */
  void deleteByOrderId(String orderId);

  /**
   * 根据订单ID查询
   *
   * @param orderId
   * @return
   */
  List<OrderPayType> findByOrderId(String orderId);
}