package com.lframework.xingyun.basedata.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum ProductSkuType implements BaseEnum<Integer> {
  SINGLE(1, "单SKU"), MULTI(2, "多SKU");

  @EnumValue
  private final Integer code;

  private final String desc;

  ProductSkuType(Integer code, String desc) {

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
