package com.lframework.xingyun.sc.dto.retail;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class RetailProductDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * SKU ID
   */
  private String skuId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 商品编号
   */
  private String code;

  /**
   * SKU编号
   */
  private String skuCode;

  /**
   * 销售属性
   */
  private String salePropertyText;

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
   * 规格
   */
  private String spec;

  /**
   * 单位
   */
  private String unit;

  /**
   * 零售价
   */
  private BigDecimal retailPrice;

  /**
   * 库存数量
   */
  private BigDecimal stockNum;

  /**
   * 税率（%）
   */
  private BigDecimal taxRate;

  /**
   * 状态
   */
  private Boolean available;
}
