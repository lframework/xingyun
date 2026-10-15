package com.lframework.xingyun.sc.vo.purchase.requisition;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class GenerateRequisitionVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "来源生产工单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "生产工单ID不能为空！")
  private String productionOrderId;

  @Schema(description = "目标收货仓库ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "仓库ID不能为空！")
  private String scId;

  @Schema(description = "明细列表", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "明细不能为空！")
  @Valid
  private List<RequisitionDetailItemVo> items;
}
