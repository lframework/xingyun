package com.lframework.xingyun.produce.vo.order;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductionOrderVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "BOM ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "BOM不能为空！")
  private String bomId;

  @Schema(description = "仓库ID（成品入库仓）", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "仓库不能为空！")
  private String scId;

  @Schema(description = "计划生产数量", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "计划生产数量不能为空！")
  private BigDecimal planNum;

  @Schema(description = "计划开始日期（yyyy-MM-dd）", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "计划开始日期不能为空！")
  private String planStartDate;

  @Schema(description = "计划完工日期（yyyy-MM-dd）", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "计划完工日期不能为空！")
  private String planEndDate;

  @Schema(description = "来源销售订单ID（可选）")
  private String saleOrderId;

  @Schema(description = "负责人ID")
  private String ownerId;

  @Schema(description = "备注")
  private String description;
}
