package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenQueryWidthType implements BaseEnum<Integer> {
  FIX(0, "固定宽度"), MIN(1, "最小宽度");

  @EnumValue
  private final Integer code;

  private final String desc;

  GenQueryWidthType(Integer code, String desc) {

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
