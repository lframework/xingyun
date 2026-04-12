package com.lframework.xingyun.settle.vo.check.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCustomerSettleCheckSheetVo extends CreateCustomerSettleCheckSheetVo {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "ID不能为空！")
  private String id;
}
