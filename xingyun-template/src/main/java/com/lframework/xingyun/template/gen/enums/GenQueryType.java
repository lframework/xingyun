package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenQueryType implements BaseEnum<Integer> {
  EQ(0, "=", "="), GT(1, ">", ">"), GE(2, ">=", ">="), LT(3, "<", "<"), LE(4, "<=", "<="), NE(5, "!=", "!="), IN(6, "IN", "IN"), NOT_IN(7,
          "NOT IN", "NOT IN"), LEFT_LIKE(8, "LIKE", "LIKE %?"), RIGHT_LIKE(9, "LIKE", "LIKE ?%"), AROUND_LIKE(10, "LIKE", "LIKE %?%");

  @EnumValue
  private final Integer code;

  private final String operation;

  private final String desc;

  GenQueryType(Integer code, String operation, String desc) {

    this.code = code;
    this.operation = operation;
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

  public String getOperation() {
    return operation;
  }
}
