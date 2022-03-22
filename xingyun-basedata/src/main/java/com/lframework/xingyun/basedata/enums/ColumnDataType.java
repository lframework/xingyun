package com.lframework.xingyun.basedata.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum ColumnDataType implements BaseEnum<Integer> {
  INT(1, "整数型"), FLOAT(2, "浮点型"), STRING(3, "字符型"), DATE(4, "日期型"), TIME(5, "时间型"), DATE_TIME(6,
      "日期时间型");

  @EnumValue
  private Integer code;

  private String desc;

  ColumnDataType(Integer code, String desc) {

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
