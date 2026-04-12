package com.lframework.xingyun.sc.vo.sale;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApproveRefuseSaleOrderVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 订单ID
   */
  @Schema(description = "订单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "订单ID不能为空！")
  private String id;

  /**
   * 拒绝理由
   */
  @Schema(description = "拒绝理由", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "拒绝理由不能为空！")
  private String refuseReason;
}
