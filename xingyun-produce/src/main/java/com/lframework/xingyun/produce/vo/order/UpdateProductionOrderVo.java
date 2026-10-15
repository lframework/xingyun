package com.lframework.xingyun.produce.vo.order;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProductionOrderVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "工单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "工单ID不能为空！")
  private String id;

  @Schema(description = "计划开始日期（yyyy-MM-dd）")
  private String planStartDate;

  @Schema(description = "计划完工日期（yyyy-MM-dd）")
  private String planEndDate;

  @Schema(description = "负责人ID")
  private String ownerId;

  @Schema(description = "备注")
  private String description;
}
