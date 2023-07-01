package com.lframework.xingyun.template.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum SysDataPermissionModelDetailNodeType implements BaseEnum<Integer> {

  CALC(1, "运算节点"), CONDITION(2, "条件节点");

  @EnumValue
  private final Integer code;

  private final String desc;

  SysDataPermissionModelDetailNodeType(Integer code, String desc) {

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
