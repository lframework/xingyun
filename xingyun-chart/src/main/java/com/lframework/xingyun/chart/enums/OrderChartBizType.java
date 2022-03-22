package com.lframework.xingyun.chart.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum OrderChartBizType implements BaseEnum<Integer> {
  PURCHASE_ORDER(0, "采购订单"), PURCHASE_RETURN(1, "采购退单"), SALE_ORDER(2, "销售订单"), SALE_RETURN(3,
      "销售退单"), RETAIL_OUT_SHEET(4, "零售出库单"), RETAIL_RETURN(5, "零售退单");

  @EnumValue
  private final Integer code;

  private final String desc;

  OrderChartBizType(Integer code, String desc) {

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
