package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenRelaMode implements BaseEnum<Integer> {
  LEFT_JOIN(0, "LEFT", "左连接"),
  RIGHT_JOIN(1, "RIGHT", "右连接"),
  INNER_JOIN(2, "INNER", "全连接");

  @EnumValue
  private final Integer code;

  private final String sql;

  private final String desc;

  GenRelaMode(Integer code, String sql, String desc) {
    this.code = code;
    this.sql = sql;
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

  public String getSql() {
    return sql;
  }
}
