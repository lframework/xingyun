package com.lframework.xingyun.basedata.dto.product.info;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class StockCostAdjustProductDto implements BaseDto, Serializable {

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
   * 类目ID
   */
  private String categoryId;

  /**
   * 类目名称
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
   * 档案采购价
   */
  private BigDecimal purchasePrice;

  /**
   * 库存数量
   */
  private Integer stockNum;

  /**
   * 调价前成本价
   */
  private BigDecimal oriPrice;
}
