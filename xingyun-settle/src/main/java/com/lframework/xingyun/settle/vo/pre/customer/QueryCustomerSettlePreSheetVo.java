package com.lframework.xingyun.settle.vo.pre.customer;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.PageVo;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.enums.CustomerSettlePreSheetStatus;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryCustomerSettlePreSheetVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 业务单据号
   */
  @ApiModelProperty("业务单据号")
  private String code;

  /**
   * 客户ID
   */
  @ApiModelProperty("客户ID")
  private String customerId;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建起始时间
   */
  @ApiModelProperty("创建起始时间")
  private LocalDateTime createStartTime;

  /**
   * 创建截止时间
   */
  @ApiModelProperty("创建截止时间")
  private LocalDateTime createEndTime;

  /**
   * 审核人
   */
  @ApiModelProperty("审核人")
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
   * 审核状态
   */
  @ApiModelProperty("审核状态")
  @IsEnum(message = "审核状态格式不正确！", enumClass = CustomerSettlePreSheetStatus.class)
  private Integer status;

  /**
   * 结算状态
   */
  @ApiModelProperty("结算状态")
  @IsEnum(message = "结算状态格式不正确！", enumClass = SettleStatus.class)
  private Integer settleStatus;
}
