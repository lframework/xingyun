package com.lframework.xingyun.sc.dto.stock.adjust.stock;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class StockAdjustProductDto implements BaseDto, Serializable {

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
   * SKU编号
   */
  private String skuCode;

  /**
   * 销售属性
   */
  private String salePropertyText;

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
   * 规格
   */
  private String spec;

  /**
   * 单位
   */
  private String unit;

  /**
   * 当前库存数量
   */
  private Integer curStockNum;
}
