package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenCustomListDetailType implements BaseEnum<Integer> {
  MAIN_TABLE(1, "主表"),
  SUB_TALBE(2, "子表"),
  CUSTOM(3, "自定义查询");

  @EnumValue
  private final Integer code;

  private final String desc;

  GenCustomListDetailType(Integer code, String desc) {

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
