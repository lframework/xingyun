package com.lframework.xingyun.sc.excel.stock.warning;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.core.annotations.excel.ExcelRequired;
import com.lframework.starter.web.core.components.excel.ExcelModel;
import lombok.Data;

@Data
public class StockWarningImportModel implements ExcelModel {

  /**
   * 仓库ID
   */
  @ExcelIgnore
  private String scId;

  /**
   * 仓库编号
   */
  @ExcelRequired
  @ExcelProperty("仓库编号")
  private String scCode;

  /**
   * 商品ID
   */
  @ExcelIgnore
  private String productId;

  /**
   * 商品编号
   */
  @ExcelRequired
  @ExcelProperty("商品编号")
  private String productCode;

  /**
   * 预警上限
   */
  @ExcelRequired
  @ExcelProperty("预警上限")
  private Integer maxLimit;

  /**
   * 预警下限
   */
  @ExcelRequired
  @ExcelProperty("预警下限")
  private Integer minLimit;
}
