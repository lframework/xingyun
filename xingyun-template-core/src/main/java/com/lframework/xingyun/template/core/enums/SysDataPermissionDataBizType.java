package com.lframework.xingyun.template.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum SysDataPermissionDataBizType implements BaseEnum<Integer> {
  ROLE(0, "角色"), DEPT(1, "部门"), USER(2, "用户");

  @EnumValue
  private final Integer code;

  private final String desc;

  SysDataPermissionDataBizType(Integer code, String desc) {

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
