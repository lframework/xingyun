package com.lframework.xingyun.basedata.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum TaxRate implements BaseEnum<Integer> {
  RATE17(17, "17%"), RATE11(11, "11%"), RATE6(6, "6%"), RATE3(3, "3%"), RATE0(0, "0%"), FREE(-1,
      "免税");

  @EnumValue
  private final Integer code;

  private final String desc;

  TaxRate(Integer code, String desc) {

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
