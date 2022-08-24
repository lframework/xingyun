package com.lframework.xingyun.settle.facade.vo.pre.customer;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApproveRefuseCustomerSettlePreSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 拒绝理由
   */
  @ApiModelProperty(value = "拒绝理由", required = true)
  @NotBlank(message = "拒绝理由不能为空！")
  private String refuseReason;
}
