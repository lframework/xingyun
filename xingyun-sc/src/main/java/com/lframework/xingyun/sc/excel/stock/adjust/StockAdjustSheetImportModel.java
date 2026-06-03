package com.lframework.xingyun.sc.excel.stock.adjust;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.core.annotations.excel.ExcelRequired;
import com.lframework.starter.web.core.components.excel.ExcelModel;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class StockAdjustSheetImportModel implements ExcelModel {

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
   * 业务类型
   */
  @ExcelIgnore
  private Integer bizType;

  /**
   * 业务类型名称
   */
  @ExcelRequired
  @ExcelProperty("业务类型")
  private String bizTypeName;

  /**
   * 调整原因ID
   */
  @ExcelIgnore
  private String reasonId;

  /**
   * 调整原因编号
   */
  @ExcelRequired
  @ExcelProperty("调整原因编号")
  private String reasonCode;

  /**
   * 商品ID
   */
  @ExcelIgnore
  private String productId;

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

  /**
   * 调整库存数量
   */
  @ExcelRequired
  @ExcelProperty("调整库存数量")
  private BigDecimal stockNum;

  /**
   * 单据明细备注
   */
  @ExcelProperty("单据明细备注")
  private String detailDescription;

  /**
   * 单据备注
   */
  @ExcelProperty("单据备注")
  private String description;
}
