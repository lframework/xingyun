package com.lframework.xingyun.template.inner.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

/**
 * @author zmj
 * @since 2022/8/20
 */
public enum QrtzJobType implements BaseEnum<Integer> {
  EXCUTE_CLASS(1, "指定类"),
  GROOVY(2, "Groovy脚本");

  @EnumValue
  private final Integer code;

  private final String desc;

  QrtzJobType(Integer code, String desc) {
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
