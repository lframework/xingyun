package com.lframework.xingyun.basedata.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum AddressType implements BaseEnum<Integer> {
  DELIVERY(1, "发货地址"), RECEIVE(2, "收货地址");

  @EnumValue
  private final Integer code;

  private final String desc;

  AddressType(Integer code, String desc) {

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
