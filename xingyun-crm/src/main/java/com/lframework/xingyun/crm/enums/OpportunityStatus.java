package com.lframework.xingyun.crm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

/**
 * 商机状态
 */
public enum OpportunityStatus implements BaseEnum<Integer> {

  OPEN(0, "进行中"),
  WON(1, "赢单"),
  LOST(2, "输单");

  @EnumValue
  private final Integer code;
  private final String desc;

  OpportunityStatus(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  @Override
  public Integer getCode() { return code; }

  @Override
  public String getDesc() { return desc; }
}
