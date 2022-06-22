package com.lframework.xingyun.crm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum DownGradeCycle implements BaseEnum<Integer> {
  DAY(1, "每天"),
  WEEK(2, "每周"),
  MONTH(3, "每月"),
  QUARTER(4, "每季度"),
  HALF_YEAR(5, "每半年"),
  YEAR(6, "每年");

  @EnumValue
  private final Integer code;

  private final String desc;

  DownGradeCycle(Integer code, String desc) {
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
