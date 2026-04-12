package com.lframework.xingyun.basedata.bo.product.info;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductBundle;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductBundleBo extends BaseBo<ProductBundle> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 单品ID
   */
  @Schema(description = "单品ID")
  private String productId;

  /**
   * 包含数量
   */
  @Schema(description = "包含数量")
  private Integer bundleNum;

  /**
   * 采购价
   */
  @Schema(description = "采购价")
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  @Schema(description = "销售价")
  private BigDecimal salePrice;

  /**
   * 零售价
   */
  @Schema(description = "零售价")
  private BigDecimal retailPrice;

  public ProductBundleBo() {
  }

  public ProductBundleBo(ProductBundle dto) {
    super(dto);
  }
}
