package com.lframework.xingyun.sc.vo.retail.returned;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.sc.enums.RetailReturnStatus;
import com.lframework.xingyun.sc.enums.SettleStatus;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryRetailReturnVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 单号
   */
  @ApiModelProperty("单号")
  private String code;

  /**
   * 会员ID
   */
  @ApiModelProperty("会员ID")
  private String memberId;

  /**
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

  /**
   * 操作人ID
   */
  @ApiModelProperty("操作人ID")
  private String createBy;

  /**
   * 操作起始时间
   */
  @ApiModelProperty("操作起始时间")
  private LocalDateTime createStartTime;

  /**
   * 操作截止时间
   */
  @ApiModelProperty("操作截止时间")
  private LocalDateTime createEndTime;

  /**
   * 审核人ID
   */
  @ApiModelProperty("审核人ID")
  private String approveBy;

  /**
   * 审核起始时间
   */
  @ApiModelProperty("审核起始时间")
  private LocalDateTime approveStartTime;

  /**
   * 审核截止时间
   */
  @ApiModelProperty("审核截止时间")
  private LocalDateTime approveEndTime;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  @IsEnum(message = "状态格式不正确！", enumClass = RetailReturnStatus.class)
  private Integer status;

  /**
   * 销售员ID
   */
  @ApiModelProperty("销售员ID")
  private String salerId;

  /**
   * 出库单号
   */
  @ApiModelProperty("出库单号")
  private String outSheetCode;

  /**
   * 结算状态
   */
  @ApiModelProperty("结算状态")
  @IsEnum(message = "结算状态格式不正确！", enumClass = SettleStatus.class)
  private Integer settleStatus;
}
