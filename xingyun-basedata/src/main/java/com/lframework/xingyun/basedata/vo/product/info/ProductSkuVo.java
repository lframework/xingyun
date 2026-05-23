package com.lframework.xingyun.basedata.vo.product.info;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class ProductSkuVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "SKU ID")
  private String id;

  @Schema(description = "SKU编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @IsCode
  @NotBlank(message = "请输入SKU编号！")
  private String code;

  @Schema(description = "扩展编号")
  private List<String> multiCodes;

  @Schema(description = "SKU主图")
  private String mainImage;

  @Schema(description = "销售属性")
  private List<SalePropertyVo> saleProperties;

  @Schema(description = "采购价")
  @Digits(integer = 10, fraction = 6, message = "采购价最多允许6位小数！")
  private BigDecimal purchasePrice;

  @Schema(description = "销售价")
  @Digits(integer = 10, fraction = 6, message = "销售价最多允许6位小数！")
  private BigDecimal salePrice;

  @Schema(description = "零售价")
  @Digits(integer = 10, fraction = 6, message = "零售价最多允许6位小数！")
  private BigDecimal retailPrice;

  @Data
  public static class SalePropertyVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "销售属性ID")
    private String propertyId;

    @Schema(description = "销售属性值ID")
    private String propertyItemId;
  }
}
