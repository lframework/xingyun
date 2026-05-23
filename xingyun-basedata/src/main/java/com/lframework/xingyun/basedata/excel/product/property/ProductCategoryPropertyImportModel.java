package com.lframework.xingyun.basedata.excel.product.property;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.core.annotations.excel.ExcelRequired;
import com.lframework.starter.web.core.components.excel.ExcelModel;
import lombok.Data;

@Data
public class ProductCategoryPropertyImportModel implements ExcelModel {

  /**
   * 属性ID
   */
  @ExcelIgnore
  private String id;

  /**
   * 属性编号
   */
  @ExcelRequired
  @ExcelProperty("属性编号")
  private String code;

  /**
   * 属性名称
   */
  @ExcelRequired
  @ExcelProperty("属性名称")
  private String name;

  /**
   * 是否必填
   */
  @ExcelRequired
  @ExcelProperty("是否必填")
  private String isRequired;

  /**
   * 字段类型
   */
  @ExcelRequired
  @ExcelProperty("字段类型")
  private String columnType;

  /**
   * 属性值编号
   */
  @ExcelProperty("属性值编号")
  private String itemCode;

  /**
   * 属性值名称
   */
  @ExcelProperty("属性值名称")
  private String itemName;
}
