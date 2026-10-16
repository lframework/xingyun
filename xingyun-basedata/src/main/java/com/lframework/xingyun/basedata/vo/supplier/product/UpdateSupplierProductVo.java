package com.lframework.xingyun.basedata.vo.supplier.product;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateSupplierProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "ID不能为空！")
  private String id;

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
  @NotNull(message = "请选择是否默认供应商！")
  private Boolean isDefault;

  @Schema(description = "备注")
  private String description;
}