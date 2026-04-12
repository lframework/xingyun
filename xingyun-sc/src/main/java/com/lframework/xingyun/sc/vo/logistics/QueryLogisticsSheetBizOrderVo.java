package com.lframework.xingyun.sc.vo.logistics;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryLogisticsSheetBizOrderVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 单号
   */
  @Schema(description = "单号")
  private String code;

  /**
   * 操作人ID
   */
  @Schema(description = "操作人ID")
  private String createBy;

  /**
   * 操作起始时间
   */
  @Schema(description = "操作起始时间")
  private LocalDateTime createStartTime;

  /**
   * 操作截止时间
   */
  @Schema(description = "操作截止时间")
  private LocalDateTime createEndTime;
}
