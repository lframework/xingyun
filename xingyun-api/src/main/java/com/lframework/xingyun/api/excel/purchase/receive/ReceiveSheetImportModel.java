package com.lframework.xingyun.api.excel.purchase.receive;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.components.excel.ExcelModel;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class ReceiveSheetImportModel implements ExcelModel {

  /**
   * 仓库ID
   */
  @ExcelIgnore
  private String scId;

  /**
   * 仓库编号
   */
  @ExcelProperty("仓库编号")
  private String scCode;

  /**
   * 供应商ID
   */
  @ExcelIgnore
  private String supplierId;

  /**
   * 供应商编号
   */
  @ExcelProperty("供应商编号")
  private String supplierCode;

  /**
   * 采购员ID
   */
  @ExcelIgnore
  private String purchaserId;

  /**
   * 采购员编号
   */
  @ExcelProperty("采购员编号")
  private String purchaserCode;

  /**
   * 付款日期
   */
  @ExcelProperty("付款日期")
  private Date paymentDate;

  /**
   * 实际到货日期
   */
  @ExcelProperty("实际到货日期")
  private Date receiveDate;

  /**
   * 商品ID
   */
  @ExcelIgnore
  private String productId;

  /**
   * 商品编号
   */
  @ExcelProperty("商品编号")
  private String productCode;

  /**
   * 采购价
   */
  @ExcelProperty("采购价")
  private BigDecimal purchasePrice;

  /**
   * 收货数量
   */
  @ExcelProperty("收货数量")
  private Integer receiveNum;

  /**
   * 是否赠品
   */
  @ExcelProperty("是否赠品")
  private String gift;

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
