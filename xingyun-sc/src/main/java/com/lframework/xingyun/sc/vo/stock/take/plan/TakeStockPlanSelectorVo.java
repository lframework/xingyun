package com.lframework.xingyun.sc.vo.stock.take.plan;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.PageVo;
import com.lframework.xingyun.sc.enums.TakeStockPlanStatus;
import com.lframework.xingyun.sc.enums.TakeStockPlanType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TakeStockPlanSelectorVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务单据号
   */
  @ApiModelProperty("业务单据号")
  private String code;

  /**
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

  /**
   * 是否正在进行盘点
   */
  @ApiModelProperty("是否正在进行盘点")
  @TypeMismatch(message = "是否正在进行盘点格式有误！")
  private Boolean taking;

  /**
   * 盘点类别
   */
  @ApiModelProperty("盘点类别")
  @TypeMismatch(message = "盘点类别格式有误！")
  @IsEnum(message = "盘点类别格式有误！", enumClass = TakeStockPlanType.class)
  private Integer takeType;

  /**
   * 盘点状态
   */
  @ApiModelProperty("盘点状态")
  @TypeMismatch(message = "盘点状态格式有误！")
  @IsEnum(message = "盘点状态格式有误！", enumClass = TakeStockPlanStatus.class)
  private Integer takeStatus;

  /**
   * 创建时间 起始时间
   */
  @ApiModelProperty("创建时间 起始时间")
  @TypeMismatch(message = "创建时间起始时间格式有误！")
  private LocalDateTime createTimeStart;

  /**
   * 创建时间 截止时间
   */
  @ApiModelProperty("创建时间 截止时间")
  @TypeMismatch(message = "创建时间截止时间格式有误！")
  private LocalDateTime createTimeEnd;

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

}
