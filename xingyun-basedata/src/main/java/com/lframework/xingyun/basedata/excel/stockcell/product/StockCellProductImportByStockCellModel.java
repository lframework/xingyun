package com.lframework.xingyun.basedata.excel.stockcell.product;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.core.annotations.excel.ExcelRequired;
import com.lframework.starter.web.core.components.excel.ExcelModel;
import lombok.Data;

@Data
public class StockCellProductImportByStockCellModel implements ExcelModel {

  /**
   * SKU ID
   */
  @ExcelIgnore
  private String skuId;

  /**
   * SKU编号
   */
  @ExcelRequired
  @ExcelProperty("SKU编号")
  private String skuCode;
}
