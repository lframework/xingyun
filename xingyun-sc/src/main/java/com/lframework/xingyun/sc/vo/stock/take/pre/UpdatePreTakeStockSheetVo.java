package com.lframework.xingyun.sc.vo.stock.take.pre;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePreTakeStockSheetVo extends CreatePreTakeStockSheetVo {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "id不能为空！")
  private String id;
}
