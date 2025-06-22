package com.lframework.xingyun.sc.vo.stock.transfer;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QueryScTransferOrderDetailReceiveVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "调拨单ID", required = true)
  @NotBlank(message = "调拨单ID不能为空！")
  private String orderId;

  @ApiModelProperty(value = "明细ID", required = true)
  @NotBlank(message = "明细ID不能为空！")
  private String detailId;
}
