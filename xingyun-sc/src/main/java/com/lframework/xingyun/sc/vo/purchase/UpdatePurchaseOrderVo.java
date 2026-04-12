package com.lframework.xingyun.sc.vo.purchase;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePurchaseOrderVo extends CreatePurchaseOrderVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 订单ID
   */
  @Schema(description = "订单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "订单ID不能为空！")
  private String id;

  @Schema(description = "是否为表单数据")
  private Boolean isForm = Boolean.FALSE;
}
