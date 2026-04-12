package com.lframework.xingyun.settle.vo.check;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApproveRefuseSettleCheckSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 拒绝理由
   */
  @Schema(description = "拒绝理由", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "拒绝理由不能为空！")
  private String refuseReason;
}
