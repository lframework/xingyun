package com.lframework.xingyun.produce.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum BomStatus implements BaseEnum<Integer> {
  DRAFT(0, "草稿"), ENABLED(1, "启用"), DISABLED(2, "停用");

  @EnumValue
  private final Integer code;

  private final String desc;

  BomStatus(Integer code, String desc) {
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
