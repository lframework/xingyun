package com.lframework.xingyun.basedata.dto.product;

import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.xingyun.basedata.enums.ProductType;
import java.io.Serializable;
import lombok.Data;

@Data
public class ProductSkuSelectorDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 选择器ID，当前为SKU ID
   */
  private String id;

  /**
   * SKU ID
   */
  private String skuId;

  /**
   * SKU编号
   */
  private String skuCode;

  /**
   * 兼容旧前端字段，返回SKU编号
   */
  private String code;

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
  private String name;

  /**
   * 商品名称
   */
  private String productName;

  /**
   * 销售属性
   */
  private String salePropertyText;

  private String categoryId;

  private String categoryName;

  private String brandId;

  private String brandName;

  private String spec;

  private String unit;

  private ProductType productType;

  private Boolean available;
}
