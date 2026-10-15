package com.lframework.xingyun.produce.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

/**
 * 生产工单状态
 */
public enum ProductionOrderStatus implements BaseEnum<Integer> {

  PENDING(0, "待排产"),
  SCHEDULED(1, "已排产"),
  IN_PROGRESS(2, "生产中"),
  COMPLETED(3, "已完工"),
  CANCELLED(4, "已取消");

  @EnumValue
  private final Integer code;
  private final String desc;

  ProductionOrderStatus(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  @Override
  public Integer getCode() { return code; }

  @Override
  public String getDesc() { return desc; }
}
