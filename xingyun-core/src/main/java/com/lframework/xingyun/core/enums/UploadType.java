package com.lframework.xingyun.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum UploadType implements BaseEnum<String> {
  LOCAL("LOCAL", "本地"),
  OSS("OSS", "阿里云OSS"),
  COS("COS", "腾讯云COS"),
  OBS("OBS", "华为云OBS");

  @EnumValue
  private final String code;

  private final String desc;

  UploadType(String code, String desc) {
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
