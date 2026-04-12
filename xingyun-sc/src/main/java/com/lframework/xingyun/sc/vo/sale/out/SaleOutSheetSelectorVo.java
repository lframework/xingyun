package com.lframework.xingyun.sc.vo.sale.out;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.PageVo;
import com.lframework.xingyun.sc.enums.SaleOutSheetStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SaleOutSheetSelectorVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 单号
   */
  @Schema(description = "单号")
  private String code;

  /**
   * 客户ID
   */
  @Schema(description = "客户ID")
  private String customerId;

  /**
   * 仓库ID
   */
  @Schema(description = "仓库ID")
  private String scId;

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
   * 状态
   */
  @Schema(description = "状态")
  @IsEnum(message = "状态格式不正确！", enumClass = SaleOutSheetStatus.class)
  private Integer status;
}
