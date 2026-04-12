package com.lframework.xingyun.sc.vo.stock.warning;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProductStockWarningVo extends CreateProductStockWarningVo {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 状态
   */
  @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "状态不能为空！")
  private Boolean available;
}
