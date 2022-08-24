package com.lframework.xingyun.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum SettleStatus implements BaseEnum<Integer> {
  UN_SETTLE(0, "未结算"), PART_SETTLE(1, "结算中"), SETTLED(3, "已结算"), UN_REQUIRE(6, "无需结算");

  @EnumValue
  private final Integer code;

  private final String desc;

  SettleStatus(Integer code, String desc) {

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
