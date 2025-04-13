package com.lframework.xingyun.export.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum ExportTaskStatus implements BaseEnum<Integer> {
  CREATED(0, "等待导出"),
  EXPORTING(1, "正在导出"),
  SUCCESS(2, "导出成功"),
  FAIL(3, "导出失败");

  @EnumValue
  private final Integer code;

  private final String desc;

  ExportTaskStatus(Integer code, String desc) {

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
