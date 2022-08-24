package com.lframework.xingyun.sc.facade.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum PurchaseOrderStatus implements BaseEnum<Integer> {
  CREATED(0, "待审核"), APPROVE_PASS(3, "审核通过"), APPROVE_REFUSE(6, "审核拒绝");

  @EnumValue
  private Integer code;

  private String desc;

  PurchaseOrderStatus(Integer code, String desc) {

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
