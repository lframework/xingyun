package com.lframework.xingyun.template.inner.enums.system;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum SysMailMessageSendStatus implements BaseEnum<Integer> {

  UN_SEND(0, "待发送"), SENDING(1, "发送中"), SENDED(2, "已发送"), FAIL(9, "发送失败");

  @EnumValue
  private final Integer code;

  private final String desc;

  SysMailMessageSendStatus(Integer code, String desc) {

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
