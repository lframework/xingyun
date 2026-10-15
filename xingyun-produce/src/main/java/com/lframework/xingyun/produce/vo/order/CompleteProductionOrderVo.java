package com.lframework.xingyun.produce.vo.order;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompleteProductionOrderVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "工单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "工单ID不能为空！")
  private String id;

  @Schema(description = "实际生产数量", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "实际生产数量不能为空！")
  private BigDecimal actualNum;

  @Schema(description = "实际完工日期（yyyy-MM-dd）")
  private String actualEndDate;

  @Schema(description = "备注")
  private String description;
}
