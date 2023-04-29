package com.lframework.xingyun.sc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum StockAdjustSheetBizType implements BaseEnum<Integer> {
  IN(0, "入库"), OUT(2, "出库");

  @EnumValue
  private Integer code;

  private String desc;

  StockAdjustSheetBizType(Integer code, String desc) {

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
