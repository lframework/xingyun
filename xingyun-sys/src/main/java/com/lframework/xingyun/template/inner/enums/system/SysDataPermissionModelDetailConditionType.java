package com.lframework.xingyun.template.inner.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum SysDataPermissionModelDetailConditionType implements BaseEnum<Integer> {

  EQ(0, "=", "等于"), GT(1, ">", "大于"), GE(2, ">=", "大于或等于"), LT(3, "<", "小于"), LE(4, "<=",
      "小于或等于"), NE(5,
      "!=", "不等于"), IN(6, "IN", "在列表中"), NOT_IN(7,
      "NOT IN", "不在列表中"), LEFT_LIKE(8, "LIKE", "结尾"), RIGHT_LIKE(9, "LIKE",
      "开头"), AROUND_LIKE(10, "LIKE", "包含");

  @EnumValue
  private final Integer code;

  private final String operation;

  private final String desc;

  SysDataPermissionModelDetailConditionType(Integer code, String operation, String desc) {

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
