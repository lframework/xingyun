package com.lframework.xingyun.basedata.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum ProductSkuCodeType implements BaseEnum<Integer> {
  SKU_CODE(1, "SKU编号"), PRODUCT_CODE(2, "商品编号"), EXTRA_CODE(3, "扩展编号");

  @EnumValue
  private final Integer code;

  private final String desc;

  ProductSkuCodeType(Integer code, String desc) {

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
