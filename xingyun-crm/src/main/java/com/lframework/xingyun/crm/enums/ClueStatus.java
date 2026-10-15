package com.lframework.xingyun.crm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

/**
 * 线索状态
 */
public enum ClueStatus implements BaseEnum<Integer> {

  PENDING(0, "待跟进"),
  FOLLOWING(1, "跟进中"),
  CONVERTED(2, "已转化"),
  INVALID(3, "无效");

  @EnumValue
  private final Integer code;
  private final String desc;

  ClueStatus(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  @Override
  public Integer getCode() { return code; }

  @Override
  public String getDesc() { return desc; }
}
