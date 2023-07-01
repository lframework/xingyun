package com.lframework.xingyun.template.inner.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum TriggerState implements BaseEnum<String> {
  WAITING("WAITING", "等待执行"),
  PAUSED("PAUSED", "暂停执行"),
  ACQUIRED("ACQUIRED", "正常执行"),
  BLOCKED("BLOCKED", "阻塞"),
  ERROR("ERROR", "执行错误"),
  COMPLETE("COMPLETE", "执行完毕"),
  PAUSED_BLOCKED("PAUSED_BLOCKED", "阻塞暂停");

  @EnumValue
  private final String code;

  private final String desc;

  TriggerState(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public String getDesc() {
    return this.desc;
  }
}
