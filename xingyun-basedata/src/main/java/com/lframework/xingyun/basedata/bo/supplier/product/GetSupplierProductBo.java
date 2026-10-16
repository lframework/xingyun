package com.lframework.xingyun.basedata.bo.supplier.product;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.SupplierProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class GetSupplierProductBo extends BaseBo<SupplierProduct> {

  @Schema(description = "ID")
  private String id;

  @Schema(description = "供应商ID")
  private String supplierId;

  @Schema(description = "商品ID")
  private String productId;

  @Schema(description = "SKU ID")
  private String skuId;

  @Schema(description = "供应商商品编号")
  private String supplierProductCode;

  @Schema(description = "供应商商品名称")
  private String supplierProductName;

  @Schema(description = "默认采购价")
  private BigDecimal defaultPurchasePrice;

  @Schema(description = "最小起订量")
  private BigDecimal minOrderQty;

  @Schema(description = "供货周期（天）")
  private Integer leadTimeDays;

  @Schema(description = "是否默认供应商")
  private Boolean isDefault;

  @Schema(description = "状态")
  private Boolean available;

  @Schema(description = "备注")
  private String description;

  public GetSupplierProductBo() {
  }

  public GetSupplierProductBo(SupplierProduct dto) {
    super(dto);
  }
}