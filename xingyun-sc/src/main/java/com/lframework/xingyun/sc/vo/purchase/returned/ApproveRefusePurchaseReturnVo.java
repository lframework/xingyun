package com.lframework.xingyun.sc.vo.purchase.returned;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApproveRefusePurchaseReturnVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 退货单ID
   */
  @Schema(description = "退货单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "退货单ID不能为空！")
  private String id;

  /**
   * 拒绝理由
   */
  @Schema(description = "拒绝理由", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "拒绝理由不能为空！")
  private String refuseReason;
}
