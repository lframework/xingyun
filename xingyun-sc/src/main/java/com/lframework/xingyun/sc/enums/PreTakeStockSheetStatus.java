package com.lframework.xingyun.sc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum PreTakeStockSheetStatus implements BaseEnum<Integer> {
  FIRST_TAKE(0, "初盘"),
  SECOND_TAKE(1, "复盘"),
  RAND_TAKE(2, "抽盘"),
  ;

  @EnumValue
  private final Integer code;

  private final String desc;

  PreTakeStockSheetStatus(Integer code, String desc) {

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
