package com.lframework.xingyun.comp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.core.enums.BaseEnum;

public enum FileBoxFileType implements BaseEnum<Integer> {
  DIR(0, "目录"),
  FILE(1, "文件"),
  ;

  @EnumValue
  private final Integer code;

  private final String desc;

  FileBoxFileType(Integer code, String desc) {
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
