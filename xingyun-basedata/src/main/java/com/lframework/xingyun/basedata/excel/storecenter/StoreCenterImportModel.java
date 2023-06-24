package com.lframework.xingyun.basedata.excel.storecenter;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.annotations.excel.ExcelRequired;
import com.lframework.starter.web.components.excel.ExcelModel;
import lombok.Data;

@Data
public class StoreCenterImportModel implements ExcelModel {

  /**
   * ID
   */
  @ExcelIgnore
  private String id;

  /**
   * 编号
   */
  @ExcelRequired
  @ExcelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ExcelRequired
  @ExcelProperty("名称")
  private String name;

  /**
   * 联系人
   */
  @ExcelProperty("联系人")
  private String contact;

  /**
   * 联系人手机号码
   */
  @ExcelProperty("联系人手机号码")
  private String telephone;

  /**
   * 地区
   */
  @ExcelProperty("地区")
  private String city;

  /**
   * 仓库地址
   */
  @ExcelProperty("仓库地址")
  private String address;

  /**
   * 仓库人数
   */
  @ExcelProperty("仓库人数")
  private Integer peopleNum;

  /**
   * 备注
   */
  @ExcelProperty("备注")
  private String description;

  /**
   * 地区的ID
   */
  @ExcelIgnore
  private String areaId;
}
