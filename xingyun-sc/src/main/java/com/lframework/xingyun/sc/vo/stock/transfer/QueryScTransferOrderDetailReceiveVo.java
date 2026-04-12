package com.lframework.xingyun.sc.vo.stock.transfer;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QueryScTransferOrderDetailReceiveVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "调拨单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "调拨单ID不能为空！")
  private String orderId;

  @Schema(description = "明细ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "明细ID不能为空！")
  private String detailId;
}
