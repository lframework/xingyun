package com.lframework.xingyun.basedata.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum PropertyType implements BaseEnum<Integer> {
  COMMON(1, "通用属性"), APPOINT(2, "指定分类属性"), NONE(3, "无");

  @EnumValue
  private final Integer code;

  private final String desc;

  PropertyType(Integer code, String desc) {

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
