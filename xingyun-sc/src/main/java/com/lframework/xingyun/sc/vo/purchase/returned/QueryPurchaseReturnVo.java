package com.lframework.xingyun.sc.vo.purchase.returned;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.SortPageVo;
import com.lframework.xingyun.sc.enums.PurchaseReturnStatus;
import com.lframework.xingyun.sc.enums.SettleStatus;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryPurchaseReturnVo extends SortPageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 单号
   */
  @ApiModelProperty("单号")
  private String code;

  /**
   * 供应商ID
   */
  @ApiModelProperty("供应商ID")
  private String supplierId;

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
  @IsEnum(message = "状态格式不正确！", enumClass = PurchaseReturnStatus.class)
  private Integer status;

  /**
   * 采购员ID
   */
  @ApiModelProperty("采购员ID")
  private String purchaserId;

  /**
   * 采购订单号
   */
  @ApiModelProperty("采购订单号")
  private String receiveSheetCode;

  /**
   * 结算状态
   */
  @ApiModelProperty("结算状态")
  @IsEnum(message = "结算状态格式不正确！", enumClass = SettleStatus.class)
  private Integer settleStatus;
}
