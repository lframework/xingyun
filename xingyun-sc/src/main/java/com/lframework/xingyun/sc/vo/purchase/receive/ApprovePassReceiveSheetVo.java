package com.lframework.xingyun.sc.vo.purchase.receive;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApprovePassReceiveSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 收货单ID
   */
  @Schema(description = "收货单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "收货单ID不能为空！")
  private String id;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
