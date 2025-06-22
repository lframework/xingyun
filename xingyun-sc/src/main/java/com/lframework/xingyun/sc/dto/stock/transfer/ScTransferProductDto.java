package com.lframework.xingyun.sc.dto.stock.transfer;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class ScTransferProductDto implements BaseDto, Serializable {

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
   * 简码
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
   * 当前库存数量
   */
  private Integer curStockNum;
}
