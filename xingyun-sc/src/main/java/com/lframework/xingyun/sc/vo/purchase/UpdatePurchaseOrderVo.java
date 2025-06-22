package com.lframework.xingyun.sc.vo.purchase;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePurchaseOrderVo extends CreatePurchaseOrderVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 订单ID
   */
  @ApiModelProperty(value = "订单ID", required = true)
  @NotBlank(message = "订单ID不能为空！")
  private String id;

  @ApiModelProperty(value = "是否为表单数据")
  private Boolean isForm = Boolean.FALSE;
}
