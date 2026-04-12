package com.lframework.xingyun.sc.vo.paytype;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderPayTypeVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID")
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 支付金额
   */
  @Schema(description = "支付金额")
  @NotNull(message = "支付金额不能为空！")
  private BigDecimal payAmount;

  /**
   * 支付内容
   */
  @Schema(description = "支付内容")
  private String text;
}
