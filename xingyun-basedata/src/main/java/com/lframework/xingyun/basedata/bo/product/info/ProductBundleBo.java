package com.lframework.xingyun.basedata.bo.product.info;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.entity.Product;
import com.lframework.xingyun.basedata.entity.ProductBundle;
import com.lframework.xingyun.basedata.entity.ProductSku;
import com.lframework.xingyun.basedata.service.product.ProductService;
import com.lframework.xingyun.basedata.service.product.ProductSkuService;
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
   * 单品编号
   */
  @Schema(description = "单品编号")
  private String productCode;

  /**
   * 单品名称
   */
  @Schema(description = "单品名称")
  private String productName;

  /**
   * 单品SKU编号
   */
  @Schema(description = "单品SKU编号")
  private String skuCode;

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

  @Override
  protected void afterInit(ProductBundle dto) {

    ProductSkuService productSkuService = ApplicationUtil.getBean(ProductSkuService.class);
    ProductSku sku = productSkuService.findById(dto.getProductId());
    if (sku == null) {
      return;
    }

    this.skuCode = sku.getCode();

    ProductService productService = ApplicationUtil.getBean(ProductService.class);
    Product product = productService.findById(sku.getProductId());
    if (product != null) {
      this.productCode = product.getCode();
      this.productName = product.getName();
    }
  }
}
