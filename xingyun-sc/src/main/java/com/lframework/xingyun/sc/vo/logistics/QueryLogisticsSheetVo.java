package com.lframework.xingyun.sc.vo.logistics;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.SortPageVo;
import com.lframework.xingyun.sc.enums.LogisticsSheetStatus;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryLogisticsSheetVo extends SortPageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 单号
   */
  @ApiModelProperty("单号")
  private String code;

  /**
   * 物流单号
   */
  @ApiModelProperty("物流单号")
  private String logisticsNo;

  /**
   * 物流公司ID
   */
  @ApiModelProperty("物流公司ID")
  private String logisticsCompanyId;

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
   * 发货人ID
   */
  @ApiModelProperty("发货人ID")
  private String deliveryBy;

  /**
   * 发货起始时间
   */
  @ApiModelProperty("发货起始时间")
  private LocalDateTime deliveryStartTime;

  /**
   * 发货截止时间
   */
  @ApiModelProperty("发货截止时间")
  private LocalDateTime deliveryEndTime;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  @IsEnum(message = "状态格式不正确！", enumClass = LogisticsSheetStatus.class)
  private Integer status;
}
