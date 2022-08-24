package com.lframework.xingyun.basedata.api.excel.product.brand;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.components.excel.ExcelModel;
import lombok.Data;

@Data
public class ProductBrandImportModel implements ExcelModel {

  /**
   * ID
   */
  @ExcelIgnore
  private String id;

  /**
   * 编号
   */
  @ExcelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ExcelProperty("名称")
  private String name;

  /**
   * 简称
   */
  @ExcelProperty("简称")
  private String shortName;

  /**
   * 简介
   */
  @ExcelProperty("简介")
  private String introduction;

  /**
   * 备注
   */
  @ExcelProperty("备注")
  private String description;
}
