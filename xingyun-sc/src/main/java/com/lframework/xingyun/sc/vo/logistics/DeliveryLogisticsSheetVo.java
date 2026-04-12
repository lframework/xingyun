package com.lframework.xingyun.sc.vo.logistics;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeliveryLogisticsSheetVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 物流单号
   */
  @Schema(description = "物流单号")
  private String logisticsNo;

  /**
   * 物流费
   */
  @Schema(description = "物流费")
  @Min(value = 0, message = "物流费必须大于0！")
  private BigDecimal totalAmount;
}
