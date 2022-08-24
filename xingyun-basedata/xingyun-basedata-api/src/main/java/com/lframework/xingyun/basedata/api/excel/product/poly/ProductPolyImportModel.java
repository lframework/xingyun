package com.lframework.xingyun.basedata.api.excel.product.poly;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.lframework.starter.web.components.excel.ExcelModel;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductPolyImportModel implements ExcelModel {

  /**
   * ID
   */
  @ExcelIgnore
  private String id;

  /**
   * 商品货号
   */
  @ExcelProperty("商品货号")
  private String code;

  /**
   * SPU名称
   */
  @ExcelProperty("SPU名称")
  private String name;

  /**
   * 商品简称
   */
  @ExcelProperty("商品简称")
  private String shortName;

  /**
   * 类目ID
   */
  @ExcelIgnore
  private String categoryId;

  /**
   * 类目编号
   */
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
   * 是否多销售属性
   */
  @ExcelIgnore
  private Boolean multiSaleprop;

  /**
   * 销售属性1 ID
   */
  @ExcelIgnore
  private String salePropGroupId1;

  /**
   * 销售属性1编号
   */
  @ExcelProperty("销售属性组1编号")
  private String salePropGroupCode1;

  /**
   * 销售属性2 ID
   */
  @ExcelIgnore
  private String salePropGroupId2;

  /**
   * 销售属性2编号
   */
  @ExcelProperty("销售属性组2编号")
  private String salePropGroupCode2;
}
