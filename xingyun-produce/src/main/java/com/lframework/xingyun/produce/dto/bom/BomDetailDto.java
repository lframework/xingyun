package com.lframework.xingyun.produce.dto.bom;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * BOM明细 DTO（关联商品信息）
 */
@Data
public class BomDetailDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  private String id;
  private String bomId;
  private String productId;
  private String skuId;
  private BigDecimal unitNum;
  private BigDecimal scrapRate;
  private Boolean isKeyMaterial;
  private Integer sortOrder;
  private String description;

  /**
   * 子件SKU编号
   */
  private String skuCode;

  /**
   * 子件商品编号
   */
  private String productCode;

  /**
   * 子件商品名称
   */
  private String productName;

  /**
   * 子件规格
   */
  private String productSpec;

  /**
   * 子件单位
   */
  private String productUnit;
}
