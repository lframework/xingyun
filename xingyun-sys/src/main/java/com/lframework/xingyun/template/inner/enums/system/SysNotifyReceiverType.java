package com.lframework.xingyun.template.inner.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum SysNotifyReceiverType implements BaseEnum<Integer> {

  DEPT(0, "部门及其子部门"), USER(1, "用户"), ROLE(2, "角色"), USER_GROUP(3, "用户组");

  @EnumValue
  private final Integer code;

  private final String desc;

  SysNotifyReceiverType(Integer code, String desc) {

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
