package com.lframework.xingyun.basedata.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum SettleType implements BaseEnum<Integer> {
  ARBITRARILY(1, "任意指定"), CASH_ON_DELIVERY(2, "货到付款");

  @EnumValue
  private Integer code;

  private String desc;

  SettleType(Integer code, String desc) {

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
