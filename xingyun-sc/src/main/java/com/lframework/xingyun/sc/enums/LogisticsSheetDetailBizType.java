package com.lframework.xingyun.sc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum LogisticsSheetDetailBizType implements BaseEnum<Integer> {
  SALE_OUT_SHEET(1, "销售出库单"), RETAIL_OUT_SHEET(2, "零售出库单");

  @EnumValue
  private final Integer code;

  private final String desc;

  LogisticsSheetDetailBizType(Integer code, String desc) {

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
