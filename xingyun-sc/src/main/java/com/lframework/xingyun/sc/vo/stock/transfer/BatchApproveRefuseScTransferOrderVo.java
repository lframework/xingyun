package com.lframework.xingyun.sc.vo.stock.transfer;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BatchApproveRefuseScTransferOrderVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库调拨单ID
   */
  @ApiModelProperty(value = "仓库调拨单ID", required = true)
  @NotEmpty(message = "仓库调拨ID不能为空！")
  private List<String> ids;

  /**
   * 拒绝理由
   */
  @ApiModelProperty(value = "拒绝理由", required = true)
  @NotBlank(message = "拒绝理由不能为空！")
  private String refuseReason;
}
