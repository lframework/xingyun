package com.lframework.xingyun.sc.vo.logistics;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.PageVo;
import com.lframework.xingyun.sc.enums.LogisticsSheetStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class LogisticsSheetSelectorVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 单号
   */
  @Schema(description = "单号")
  private String code;

  /**
   * 物流单号
   */
  @Schema(description = "物流单号")
  private String logisticsNo;

  /**
   * 物流公司ID
   */
  @Schema(description = "物流公司ID")
  private String logisticsCompanyId;

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

  /**
   * 发货人ID
   */
  @Schema(description = "发货人ID")
  private String deliveryBy;

  /**
   * 发货起始时间
   */
  @Schema(description = "发货起始时间")
  private LocalDateTime deliveryStartTime;

  /**
   * 发货截止时间
   */
  @Schema(description = "发货截止时间")
  private LocalDateTime deliveryEndTime;

  /**
   * 状态
   */
  @Schema(description = "状态")
  @IsEnum(message = "状态格式不正确！", enumClass = LogisticsSheetStatus.class)
  private Integer status;
}
