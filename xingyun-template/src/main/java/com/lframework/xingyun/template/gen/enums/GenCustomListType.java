package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenCustomListType implements BaseEnum<Integer> {
  SEQ(0, "序列"),
  SINGLE(1, "单选"),
  MULTIPLE(2, "多选");

  @EnumValue
  private final Integer code;

  private final String desc;

  GenCustomListType(Integer code, String desc) {

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
