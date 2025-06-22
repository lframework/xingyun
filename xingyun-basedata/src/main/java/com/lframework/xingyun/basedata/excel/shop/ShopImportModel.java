package com.lframework.xingyun.basedata.excel.shop;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.core.annotations.excel.ExcelRequired;
import com.lframework.starter.web.core.components.excel.ExcelModel;
import lombok.Data;

@Data
public class ShopImportModel implements ExcelModel {

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
   * 所属部门编号
   */
  @ExcelProperty("所属部门编号")
  private String deptCode;

  /**
   * 所属部门ID
   */
  @ExcelIgnore
  private String deptId;

  /**
   * 备注
   */
  @ExcelProperty("备注")
  private String description;
}
