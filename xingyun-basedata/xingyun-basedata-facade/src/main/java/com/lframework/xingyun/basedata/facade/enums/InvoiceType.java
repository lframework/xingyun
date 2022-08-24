package com.lframework.xingyun.basedata.facade.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum InvoiceType implements BaseEnum<Integer> {
  NORMAL(1, "增值税普通发票"), SPECIAL(2, "增值税专用发票"), NORMAL_OR_SPECIAL(3, "增值税专用发票或增值税普通发票"),
  ;

  @EnumValue
  private final Integer code;

  private final String desc;

  InvoiceType(Integer code, String desc) {

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
