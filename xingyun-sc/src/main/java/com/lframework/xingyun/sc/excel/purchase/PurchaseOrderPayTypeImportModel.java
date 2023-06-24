package com.lframework.xingyun.sc.excel.purchase;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.annotations.excel.ExcelRequired;
import com.lframework.starter.web.components.excel.ExcelModel;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PurchaseOrderPayTypeImportModel implements ExcelModel {

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
   * 支付方式ID
   */
  @ExcelIgnore
  private String payTypeId;

  /**
   * 支付方式编号
   */
  @ExcelRequired
  @ExcelProperty("支付方式编号")
  private String payTypeCode;

  /**
   * 支付金额
   */
  @ExcelRequired
  @ExcelProperty("支付金额")
  private BigDecimal payAmount;

  /**
   * 支付内容
   */
  @ExcelProperty("支付内容")
  private String text;

  /**
   * 单据总金额
   */
  @ExcelIgnore
  private BigDecimal totalAmount;
}
