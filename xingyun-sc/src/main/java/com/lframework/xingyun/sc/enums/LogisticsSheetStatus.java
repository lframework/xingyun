package com.lframework.xingyun.sc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum LogisticsSheetStatus implements BaseEnum<Integer> {
  CREATED(0, "待发货"), DELIVERY(3, "已发货");

  @EnumValue
  private final Integer code;

  private final String desc;

  LogisticsSheetStatus(Integer code, String desc) {

    this.code = code;
    this.desc = desc;
  }

  @Override
  public Integer getCode() {

    return this.code;
  }

  @Override
  public String getDesc() {

    return this.desc;
  }
}
