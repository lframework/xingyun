package com.lframework.xingyun.basedata.bo.product.info;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductBundle;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductBundleBo extends BaseBo<ProductBundle> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 单品ID
   */
  @ApiModelProperty("单品ID")
  private String productId;

  /**
   * 包含数量
   */
  @ApiModelProperty("包含数量")
  private Integer bundleNum;

  /**
   * 销售价
   */
  @ApiModelProperty("销售价")
  private BigDecimal salePrice;

  /**
   * 零售价
   */
  @ApiModelProperty("零售价")
  private BigDecimal retailPrice;

  public ProductBundleBo() {
  }

  public ProductBundleBo(ProductBundle dto) {
    super(dto);
  }
}
