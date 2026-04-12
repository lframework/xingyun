package com.lframework.xingyun.settle.vo.sheet.customer;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QueryCustomerUnSettleBizItemVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 客户ID
   */
  @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "客户ID不能为空！")
  private String customerId;

  /**
   * 起始时间
   */
  @Schema(description = "起始时间")
  private LocalDateTime startTime;

  /**
   * 截至时间
   */
  @Schema(description = "截至时间")
  private LocalDateTime endTime;
}
