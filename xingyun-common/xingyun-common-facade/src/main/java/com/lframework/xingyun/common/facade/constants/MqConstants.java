package com.lframework.xingyun.common.facade.constants;

/**
 * @author zmj
 * @since 2022/8/25
 */
public interface MqConstants {

  /**
   * 创建单据时间轴
   */
  String QUEUE_ORDER_TIME_LINE_CREATE = "common:orderTimeLine:create";

  /**
   * 删除单据时间轴
   */
  String QUEUE_ORDER_TIME_LINE_DELETE = "common:orderTimeLine:delete";
}
