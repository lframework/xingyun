package com.lframework.xingyun.sc.vo.sale;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApprovePassSaleOrderVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 订单ID
   */
  @ApiModelProperty(value = "订单ID", required = true)
  @NotBlank(message = "订单ID不能为空！")
  private String id;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
