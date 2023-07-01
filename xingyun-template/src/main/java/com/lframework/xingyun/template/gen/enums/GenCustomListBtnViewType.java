package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenCustomListBtnViewType implements BaseEnum<String> {

  PRIMARY("primary", "primary"),
  DEFAULT("default", "default"),
  DASHED("dashed", "dashed"),
  DANGER("danger", "danger"),
  LINK("link", "link"),
  LINK_DANGER("link-danger", "link-danger");

  @EnumValue
  private final String code;

  private final String desc;

  GenCustomListBtnViewType(String code, String desc) {

    this.code = code;
    this.desc = desc;
  }

  @Override
  public String getCode() {

    return this.code;
  }

  @Override
  public String getDesc() {

    return this.desc;
  }
}
