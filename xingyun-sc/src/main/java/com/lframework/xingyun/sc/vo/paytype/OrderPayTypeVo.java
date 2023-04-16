package com.lframework.xingyun.sc.vo.paytype;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderPayTypeVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty("ID")
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 支付金额
   */
  @ApiModelProperty("支付金额")
  @NotNull(message = "支付金额不能为空！")
  private BigDecimal payAmount;

  /**
   * 支付内容
   */
  @ApiModelProperty("支付内容")
  private String text;
}
