package com.lframework.xingyun.sc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

/**
 * 采购建议单状态
 */
public enum PurchaseRequisitionStatus implements BaseEnum<Integer> {

  PENDING(0, "待确认"),
  CONFIRMED(1, "已确认"),
  CONVERTED(2, "已转采购单"),
  CANCELLED(3, "已取消");

  @EnumValue
  private final Integer code;
  private final String desc;

  PurchaseRequisitionStatus(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  @Override
  public Integer getCode() { return code; }

  @Override
  public String getDesc() { return desc; }
}
