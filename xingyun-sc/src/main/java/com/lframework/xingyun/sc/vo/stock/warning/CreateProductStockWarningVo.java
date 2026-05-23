package com.lframework.xingyun.sc.vo.stock.warning;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateProductStockWarningVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "仓库ID不能为空！")
  private String scId;

  /**
   * 商品ID
   */
  @Schema(description = "商品ID")
  private String productId;

  /**
   * SKU ID
   */
  @Schema(description = "SKU ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "SKU ID不能为空！")
  private String skuId;

  /**
   * 预警下限
   */
  @Schema(description = "预警下限", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "预警下限不能为空！")
  @Positive(message = "预警下限必须大于0！")
  @Digits(integer = 20, fraction = 8, message = "预警下限最多允许8位小数！")
  private BigDecimal minLimit;

  /**
   * 预警上限
   */
  @Schema(description = "预警上限", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "预警上限不能为空！")
  @Positive(message = "预警上限必须大于0！")
  @Digits(integer = 20, fraction = 8, message = "预警上限最多允许8位小数！")
  private BigDecimal maxLimit;
}
