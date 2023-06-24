package com.lframework.xingyun.basedata.excel.product;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.annotations.excel.ExcelRequired;
import com.lframework.starter.web.components.excel.ExcelModel;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductImportModel implements ExcelModel {

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
   * 简称
   */
  @ExcelProperty("简称")
  private String shortName;

  /**
   * SKU编号
   */
  @ExcelRequired
  @ExcelProperty("SKU编号")
  private String skuCode;

  /**
   * 外部编号
   */
  @ExcelProperty("外部编号")
  private String externalCode;

  /**
   * 类目ID
   */
  @ExcelIgnore
  private String categoryId;

  /**
   * 类目编号
   */
  @ExcelRequired
  @ExcelProperty("类目编号")
  private String categoryCode;

  /**
   * 品牌ID
   */
  @ExcelIgnore
  private String brandId;

  /**
   * 品牌编号
   */
  @ExcelRequired
  @ExcelProperty("品牌编号")
  private String brandCode;

  /**
   * 进项税率（%）
   */
  @ExcelProperty("进项税率（%）")
  private BigDecimal taxRate;

  /**
   * 销项税率（%）
   */
  @ExcelProperty("销项税率（%）")
  private BigDecimal saleTaxRate;

  /**
   * 规格
   */
  @ExcelProperty("规格")
  private String spec;

  /**
   * 单位
   */
  @ExcelProperty("单位")
  private String unit;

  /**
   * 采购价
   */
  @ExcelProperty("采购价（元）")
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  @ExcelProperty("销售价（元）")
  private BigDecimal salePrice;

  /**
   * 零售价
   */
  @ExcelProperty("零售价（元）")
  private BigDecimal retailPrice;
}
