package com.lframework.xingyun.template.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum Available implements BaseEnum<Boolean> {
  ENABLE(Boolean.TRUE, "启用"), UNABLE(Boolean.FALSE, "停用");


  @EnumValue
  private final Boolean code;

  private final String desc;

  Available(Boolean code, String desc) {

    this.code = code;
    this.desc = desc;
  }

  @Override
  public Boolean getCode() {

    return this.code;
  }

  @Override
  public String getDesc() {

    return this.desc;
  }
}
