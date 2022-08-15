package com.lframework.xingyun.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

/**
 * @author zmj
 * @since 2022/8/10
 */
public enum OrderTimeLineBizType implements BaseEnum<Integer> {
  NORMAL(0, "普通操作"),
  CREATE(1, "保存"),
  UPDATE(2, "修改"),
  SEND(3, "发送"),
  APPROVE_PASS(4, "审核通过"),
  APPROVE_RETURN(5, "审核拒绝"),
  CANCEL_APPROVE(6, "取消审核")
  ;

  @EnumValue
  private final Integer code;

  private final String desc;

  OrderTimeLineBizType(Integer code, String desc) {
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
