package com.lframework.xingyun.sc.vo.logistics;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeliveryLogisticsSheetVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 物流单号
   */
  @ApiModelProperty("物流单号")
  private String logisticsNo;

  /**
   * 物流费
   */
  @ApiModelProperty("物流费")
  @Min(value = 0, message = "物流费必须大于0！")
  private BigDecimal totalAmount;
}
