package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenKeyType implements BaseEnum<Integer> {
  AUTO(0, "自增ID"), UUID(1, "UUID"), SNOW_FLAKE(2, "雪花算法ID");

  @EnumValue
  private final Integer code;

  private final String desc;

  GenKeyType(Integer code, String desc) {

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
