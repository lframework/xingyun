package com.lframework.xingyun.sc.excel.logistics;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.core.annotations.excel.ExcelRequired;
import com.lframework.starter.web.core.components.excel.ExcelModel;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class LogisticsSheetDeliveryImportModel implements ExcelModel {

  /**
   * ID
   */
  @ExcelIgnore
  private String id;

  /**
   * 单据号
   */
  @ExcelRequired
  @ExcelProperty("单据号")
  private String code;

  /**
   * 物流单号
   */
  @ExcelProperty("物流单号")
  private String logisticsNo;

  /**
   * 物流费
   */
  @ExcelProperty("物流费（元）")
  private BigDecimal totalAmount;
}
