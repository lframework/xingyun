package com.lframework.xingyun.basedata.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum AddressEntityType implements BaseEnum<Integer> {
  SC(1, "仓库"), CUSTOMER(2, "客户"), SUPPLIER(3, "供应商"), MEMBER(4, "会员"), SHOP(5, "门店");

  @EnumValue
  private final Integer code;

  private final String desc;

  AddressEntityType(Integer code, String desc) {

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
