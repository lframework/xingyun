package com.lframework.xingyun.crm.vo.opportunity;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/** 商机赢单/输单 Vo */
@Data
public class CloseOpportunityVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "商机ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "商机ID不能为空！")
  private String id;

  /** true=赢单，false=输单 */
  @Schema(description = "是否赢单", requiredMode = Schema.RequiredMode.REQUIRED)
  private Boolean won;

  @Schema(description = "备注")
  private String description;
}
