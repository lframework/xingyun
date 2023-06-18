package com.lframework.xingyun.sc.vo.stock.take.sheet;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.sc.enums.TakeStockPlanStatus;
import com.lframework.xingyun.sc.enums.TakeStockSheetStatus;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryTakeStockSheetVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务单据号
   */
  @ApiModelProperty("业务单据号")
  private String code;

  /**
   * 盘点任务ID
   */
  @ApiModelProperty("盘点任务ID")
  private String planId;

  /**
   * 预先盘点单ID
   */
  @ApiModelProperty("预先盘点单ID")
  private String preSheetId;

  /**
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

  /**
   * 盘点状态
   */
  @ApiModelProperty("盘点状态")
  @TypeMismatch(message = "盘点状态格式有误！")
  @IsEnum(message = "盘点状态格式有误！", enumClass = TakeStockPlanStatus.class)
  private Integer takeStatus;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  @TypeMismatch(message = "状态格式有误！")
  @IsEnum(message = "状态格式有误！", enumClass = TakeStockSheetStatus.class)
  private Integer status;

  /**
   * 修改人
   */
  @ApiModelProperty("修改人")
  private String updateBy;

  /**
   * 修改时间 起始时间
   */
  @ApiModelProperty("修改时间 起始时间")
  @TypeMismatch(message = "修改时间起始时间格式有误！")
  private LocalDateTime updateTimeStart;

  /**
   * 修改时间 截止时间
   */
  @ApiModelProperty("修改时间 截止时间")
  @TypeMismatch(message = "修改时间截止时间格式有误！")
  private LocalDateTime updateTimeEnd;

  /**
   * 审核人
   */
  @ApiModelProperty("审核人")
  private String approveBy;

  /**
   * 审核时间 起始时间
   */
  @ApiModelProperty("审核时间 起始时间")
  @TypeMismatch(message = "审核时间起始时间格式有误！")
  private LocalDateTime approveTimeStart;

  /**
   * 审核时间 截止时间
   */
  @ApiModelProperty("审核时间 截止时间")
  @TypeMismatch(message = "审核时间截止时间格式有误！")
  private LocalDateTime approveTimeEnd;

}
