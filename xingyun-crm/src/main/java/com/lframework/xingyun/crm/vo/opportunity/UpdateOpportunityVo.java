package com.lframework.xingyun.crm.vo.opportunity;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateOpportunityVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "ID不能为空！")
  private String id;

  @Schema(description = "商机名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "商机名称不能为空！")
  private String name;

  @Schema(description = "关联客户ID")
  private String customerId;

  @Schema(description = "负责销售员ID")
  private String ownerId;

  @Schema(description = "预计金额")
  private BigDecimal estimatedAmount;

  @Schema(description = "预计成交日期")
  private LocalDate expectedCloseDate;

  @Schema(description = "阶段")
  private Integer stage;

  @Schema(description = "备注")
  private String description;
}
