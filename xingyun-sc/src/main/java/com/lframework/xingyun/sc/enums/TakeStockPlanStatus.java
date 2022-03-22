package com.lframework.xingyun.sc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum TakeStockPlanStatus implements BaseEnum<Integer> {
  CREATED(0, "盘点任务生成"),
  DIFF_CREATED(6, "盘点差异生成"),
  FINISHED(9, "盘点完成"),
  CANCELED(12, "盘点已作废");

  @EnumValue
  private final Integer code;

  private final String desc;

  TakeStockPlanStatus(Integer code, String desc) {

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
