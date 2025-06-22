package com.lframework.xingyun.sc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum ScTransferOrderStatus implements BaseEnum<Integer> {
  CREATED(0, "待审核"), APPROVE_PASS(3, "审核通过"), APPROVE_REFUSE(6, "审核拒绝"), PART_RECEIVED(9, "部分收货"), RECEIVED(12, "已收货");

  @EnumValue
  private Integer code;

  private String desc;

  ScTransferOrderStatus(Integer code, String desc) {

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
