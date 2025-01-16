package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenStatus implements BaseEnum<Integer> {
  CREATED(0, "已生成"), SET_TABLE(1, "已设置数据表"), SET_GEN(2, "已设置生成配置");

  @EnumValue
  private final Integer code;

  private final String desc;

  GenStatus(Integer code, String desc) {

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
