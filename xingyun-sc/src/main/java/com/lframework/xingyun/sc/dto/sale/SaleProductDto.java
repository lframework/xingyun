package com.lframework.xingyun.sc.dto.sale;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class SaleProductDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 分类ID
   */
  private String categoryId;

  /**
   * 分类名称
   */
  private String categoryName;

  /**
   * 品牌ID
   */
  private String brandId;

  /**
   * 品牌名称
   */
  private String brandName;

  /**
   * SKU
   */
  private String skuCode;

  /**
   * 外部编号
   */
  private String externalCode;

  /**
   * 规格
   */
  private String spec;

  /**
   * 单位
   */
  private String unit;

  /**
   * 销售价
   */
  private BigDecimal salePrice;

  /**
   * 税率（%）
   */
  private BigDecimal taxRate;

  /**
   * 状态
   */
  private Boolean available;
}
