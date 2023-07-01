package com.lframework.xingyun.template.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum SysDataPermissionModelDetailCalcType implements BaseEnum<Integer> {

  AND(1, "AND", "并且"), OR(2, "OR", "或者");

  @EnumValue
  private final Integer code;

  private String operation;

  private final String desc;

  SysDataPermissionModelDetailCalcType(Integer code, String operation, String desc) {

    this.code = code;
    this.operation = operation;
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

  public String getOperation() {
    return operation;
  }
}
