package com.lframework.xingyun.basedata.api.excel.product;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.components.excel.ExcelModel;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductImportModel implements ExcelModel {

  /**
   * polyId
   */
  @ExcelIgnore
  private String polyId;

  /**
   * ID
   */
  @ExcelIgnore
  private String id;

  /**
   * 商品货号
   */
  @ExcelProperty("商品货号")
  private String spuCode;

  /**
   * 商品编号
   */
  @ExcelProperty("商品编号")
  private String code;

  /**
   * 商品名称
   */
  @ExcelProperty("商品名称")
  private String name;

  /**
   * 商品SKU编号
   */
  @ExcelProperty("商品SKU编号")
  private String skuCode;

  /**
   * 外部编号
   */
  @ExcelProperty("外部编号")
  private String externalCode;

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

  /**
   * 销售属性1 ID
   */
  @ExcelIgnore
  private String salePropItemId1;

  /**
   * 销售属性1编号
   */
  @ExcelProperty("销售属性1编号")
  private String salePropItemCode1;

  /**
   * 销售属性2 ID
   */
  @ExcelIgnore
  private String salePropItemId2;

  /**
   * 销售属性2编号
   */
  @ExcelProperty("销售属性2编号")
  private String salePropItemCode2;
}
