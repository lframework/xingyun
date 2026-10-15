package com.lframework.xingyun.sc.vo.purchase.requisition;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdjustRequisitionDetailVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "明细ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "明细ID不能为空！")
  private String id;

  @Schema(description = "建议采购数量", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "建议采购数量不能为空！")
  private BigDecimal suggestNum;
}
