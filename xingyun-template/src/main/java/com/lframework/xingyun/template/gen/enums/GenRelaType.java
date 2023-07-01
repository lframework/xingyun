package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenRelaType implements BaseEnum<Integer> {
  ONE_RELA_ONE(0, "一对一"),
  ONE_RELA_MANY(1, "一对多");

  @EnumValue
  private final Integer code;

  private final String desc;

  GenRelaType(Integer code, String desc) {
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
