package com.lframework.xingyun.basedata.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum ColumnType implements BaseEnum<Integer> {
  MULTIPLE(1, "多选"), SINGLE(2, "单选"), CUSTOM(3, "手动录入");

  @EnumValue
  private final Integer code;

  private final String desc;

  ColumnType(Integer code, String desc) {

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
