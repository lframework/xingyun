package com.lframework.xingyun.basedata.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum StockCellType implements BaseEnum<Integer> {
  SHELF(1, "货架仓位"), STORAGE(2, "存储仓位");

  @EnumValue
  private final Integer code;

  private final String desc;

  StockCellType(Integer code, String desc) {

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
