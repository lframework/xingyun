package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenOrderType implements BaseEnum<String> {
  ASC("ASC", "升序"), DESC("DESC", "降序");

  @EnumValue
  private final String code;

  private final String desc;

  GenOrderType(String code, String desc) {

    this.code = code;
    this.desc = desc;
  }

  @Override
  public String getCode() {

    return this.code;
  }

  @Override
  public String getDesc() {

    return this.desc;
  }
}
