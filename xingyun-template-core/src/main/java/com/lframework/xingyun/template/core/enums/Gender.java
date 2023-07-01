package com.lframework.xingyun.template.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum Gender implements BaseEnum<Integer> {
  UNKNOWN(0, "未知"), MAN(1, "男"), FEMALE(2, "女");

  @EnumValue
  private final Integer code;

  private final String desc;

  Gender(Integer code, String desc) {

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
