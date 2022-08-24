package com.lframework.xingyun.settle.facade.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum CustomerSettleCheckSheetBizType implements BaseEnum<Integer> {
  OUT_SHEET(1, "销售出库单"), SALE_RETURN(2, "销售退单"), SETTLE_FEE_SHEET(3, "客户费用单"), SETTLE_PRE_SHEET(4,
      "客户预收款单");

  @EnumValue
  private final Integer code;

  private final String desc;

  CustomerSettleCheckSheetBizType(Integer code, String desc) {

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
