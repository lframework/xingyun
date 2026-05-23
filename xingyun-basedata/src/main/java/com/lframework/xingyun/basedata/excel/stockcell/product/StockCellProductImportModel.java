package com.lframework.xingyun.basedata.excel.stockcell.product;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.core.annotations.excel.ExcelRequired;
import com.lframework.starter.web.core.components.excel.ExcelModel;
import java.util.List;
import lombok.Data;

@Data
public class StockCellProductImportModel implements ExcelModel {

  /**
   * 仓库ID
   */
  @ExcelIgnore
  private String scId;

  /**
   * 仓位ID
   */
  @ExcelIgnore
  private String stockCellId;

  /**
   * SKU ID
   */
  @ExcelIgnore
  private String skuId;

  /**
   * 仓位类别
   */
  @ExcelIgnore
  private Integer cellType;

  /**
   * 仓库编号
   */
  @ExcelRequired
  @ExcelProperty("仓库编号")
  private String scCode;

  /**
   * 仓位编号
   */
  @ExcelRequired
  @ExcelProperty("仓位编号")
  private String stockCellCode;

  /**
   * SKU编号
   */
  @ExcelRequired
  @ExcelProperty("SKU编号")
  private String productCode;
}
