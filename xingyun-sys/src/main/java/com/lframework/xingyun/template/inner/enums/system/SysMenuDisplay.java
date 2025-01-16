package com.lframework.xingyun.template.inner.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum SysMenuDisplay implements BaseEnum<Integer> {

  CATALOG(0, "目录"), FUNCTION(1, "菜单"), PERMISSION(2, "权限");

  @EnumValue
  private final Integer code;

  private final String desc;

  SysMenuDisplay(Integer code, String desc) {

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
