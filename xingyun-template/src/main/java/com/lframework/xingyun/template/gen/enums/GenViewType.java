package com.lframework.xingyun.template.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum GenViewType implements BaseEnum<Integer> {
  INPUT(0, "输入框"), TEXTATREA(1, "文本域"), DATETIME(2, "日期时间选择器"), DATE(3, "日期选择器"), TIME(4,
      "时间选择器"), SELECT(5,
      "选择器"), DATE_RANGE(6, "日期范围选择器"), DATA_DIC(7, "数据字典"), CUSTOM_SELECTOR(8, "自定义选择器");

  @EnumValue
  private final Integer code;


  private final String desc;

  GenViewType(Integer code, String desc) {

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
