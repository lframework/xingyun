package com.lframework.xingyun.basedata.dto.stockcell.product;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class StockCellProductDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 仓库ID
   */
  private String scId;

  /**
   * 仓库编号
   */
  private String scCode;

  /**
   * 仓库名称
   */
  private String scName;

  /**
   * 仓位ID
   */
  private String stockCellId;

  /**
   * 仓位编号
   */
  private String stockCellCode;

  /**
   * 仓位名称
   */
  private String stockCellName;

  /**
   * 仓位类型
   */
  private Integer stockCellType;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 商品编号
   */
  private String productCode;

  /**
   * 商品名称
   */
  private String productName;

  /**
   * 商品规格
   */
  private String spec;

  /**
   * 单位
   */
  private String unit;

  /**
   * 分类ID
   */
  private String categoryId;

  /**
   * 分类编号
   */
  private String categoryCode;

  /**
   * 分类名称
   */
  private String categoryName;

  /**
   * 品牌ID
   */
  private String brandId;

  /**
   * 品牌编号
   */
  private String brandCode;

  /**
   * 品牌名称
   */
  private String brandName;
}
