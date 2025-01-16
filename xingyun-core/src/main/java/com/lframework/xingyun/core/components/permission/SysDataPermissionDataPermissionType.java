package com.lframework.xingyun.core.components.permission;

public enum SysDataPermissionDataPermissionType {

  PRODUCT(1, "商品"), ORDER(2, "单据");

  private Integer code;

  private String desc;

  SysDataPermissionDataPermissionType(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public Integer getCode() {
    return this.code;
  }

  public String getDesc() {
    return desc;
  }
}
