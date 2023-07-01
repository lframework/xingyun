package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenConvertType implements BaseEnum<Integer> {
  UNDERLINE_TO_CAMEL(1, "下划线转驼峰");

  @EnumValue
  private final Integer code;

  private final String desc;

  GenConvertType(Integer code, String desc) {

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
