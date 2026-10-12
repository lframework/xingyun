package com.lframework.xingyun.produce.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

/**
 * 组装单状态
 *
 * @author zmj
 */
public enum AssembleSheetStatus implements BaseEnum<Integer> {

  CREATED(0, "待审核"), APPROVE_PASS(3, "审核通过"), APPROVE_REFUSE(6, "审核拒绝");

  @EnumValue
  private final Integer code;

  private final String desc;

  AssembleSheetStatus(Integer code, String desc) {

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
