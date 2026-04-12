package com.lframework.xingyun.sc.vo.retail.out;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.SortPageVo;
import com.lframework.xingyun.sc.enums.RetailOutSheetStatus;
import com.lframework.xingyun.sc.enums.SettleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryRetailOutSheetVo extends SortPageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 单号
   */
  @Schema(description = "单号")
  private String code;

  /**
   * 会员ID
   */
  @Schema(description = "会员ID")
  private String memberId;

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
   * 审核人ID
   */
  @Schema(description = "审核人ID")
  private String approveBy;

  /**
   * 审核起始时间
   */
  @Schema(description = "审核起始时间")
  private LocalDateTime approveStartTime;

  /**
   * 审核截止时间
   */
  @Schema(description = "审核截止时间")
  private LocalDateTime approveEndTime;

  /**
   * 状态
   */
  @Schema(description = "状态")
  @IsEnum(message = "状态格式不正确！", enumClass = RetailOutSheetStatus.class)
  private Integer status;

  /**
   * 销售员ID
   */
  @Schema(description = "销售员ID")
  private String salerId;

  /**
   * 结算状态
   */
  @Schema(description = "结算状态")
  @IsEnum(message = "结算状态格式不正确！", enumClass = SettleStatus.class)
  private Integer settleStatus;
}
