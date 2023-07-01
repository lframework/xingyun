package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenTemplateType implements BaseEnum<Integer> {
  LIST(1, "列表"), TREE(2, "树形表");

  @EnumValue
  private final Integer code;

  private final String desc;

  GenTemplateType(Integer code, String desc) {

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
