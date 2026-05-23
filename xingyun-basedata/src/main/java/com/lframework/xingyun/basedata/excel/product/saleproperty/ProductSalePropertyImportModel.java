package com.lframework.xingyun.basedata.excel.product.saleproperty;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.core.annotations.excel.ExcelRequired;
import com.lframework.starter.web.core.components.excel.ExcelModel;
import lombok.Data;

@Data
public class ProductSalePropertyImportModel implements ExcelModel {

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
   * 属性值编号
   */
  @ExcelRequired
  @ExcelProperty("属性值编号")
  private String itemCode;

  /**
   * 属性值名称
   */
  @ExcelRequired
  @ExcelProperty("属性值名称")
  private String itemName;
}
