package com.lframework.xingyun.sc.vo.purchase.returned;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApprovePassPurchaseReturnVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 退货单ID
   */
  @ApiModelProperty(value = "退货单ID", required = true)
  @NotBlank(message = "退货单ID不能为空！")
  private String id;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
