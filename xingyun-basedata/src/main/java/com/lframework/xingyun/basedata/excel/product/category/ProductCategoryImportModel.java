package com.lframework.xingyun.basedata.excel.product.category;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.core.annotations.excel.ExcelRequired;
import com.lframework.starter.web.core.components.excel.ExcelModel;
import lombok.Data;

@Data
public class ProductCategoryImportModel implements ExcelModel {

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
   * 上级分类编号
   */
  @ExcelProperty("上级分类编号")
  private String parentCode;

  /**
   * 上级分类ID
   */
  @ExcelIgnore
  private String parentId;

  /**
   * 备注
   */
  @ExcelProperty("备注")
  private String description;

}
