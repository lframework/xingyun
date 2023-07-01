package com.lframework.xingyun.template.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum SysDataPermissionModelDetailInputType implements BaseEnum<Integer> {

  INPUT(0, "输入框"), SELECT(1, "选择器"), DATE_TIME(2, "日期时间选择器"), DATE(3,
      "日期选择器"), SQL(99, "SQL");

  @EnumValue
  private final Integer code;

  private final String desc;

  SysDataPermissionModelDetailInputType(Integer code, String desc) {

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
