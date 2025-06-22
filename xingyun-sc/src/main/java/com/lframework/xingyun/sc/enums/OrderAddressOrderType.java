package com.lframework.xingyun.sc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum OrderAddressOrderType implements BaseEnum<Integer> {
  PURCHASE_ORDER(0, "采购订单"), RECEIVE_SHEET(1, "采购收货单"), PURCHASE_RETURN(2, "采购退单"),
  SALE_ORDER(3, "采购订单"), OUT_SHEET(4, "销售出库单"), SALE_RETURN(5, "销售退单"),
  RETAIL_OUT_SHEET(6, "零售出库单"), RETAIL_RETURN(7, "销售退单");

  @EnumValue
  private final Integer code;

  private final String desc;

  OrderAddressOrderType(Integer code, String desc) {

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
