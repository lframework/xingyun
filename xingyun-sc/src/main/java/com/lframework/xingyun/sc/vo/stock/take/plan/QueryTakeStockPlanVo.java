package com.lframework.xingyun.sc.vo.stock.take.plan;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.sc.enums.TakeStockPlanStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryTakeStockPlanVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务单据号
   */
  private String code;

  /**
   * 仓库ID
   */
  private String scId;

  /**
   * 盘点状态
   */
  @TypeMismatch(message = "盘点状态格式有误！")
  @IsEnum(message = "盘点状态格式有误！", enumClass = TakeStockPlanStatus.class)
  private Integer takeStatus;

  /**
   * 创建人ID
   */
  private String createBy;

  /**
   * 创建时间 起始时间
   */
  @TypeMismatch(message = "创建时间起始时间格式有误！")
  private LocalDateTime createTimeStart;

  /**
   * 创建时间 截止时间
   */
  @TypeMismatch(message = "创建时间截止时间格式有误！")
  private LocalDateTime createTimeEnd;

  /**
   * 修改人ID
   */
  private String updateBy;

  /**
   * 修改时间 起始时间
   */
  @TypeMismatch(message = "修改时间起始时间格式有误！")
  private LocalDateTime updateTimeStart;

  /**
   * 修改时间 截止时间
   */
  @TypeMismatch(message = "修改时间截止时间格式有误！")
  private LocalDateTime updateTimeEnd;

}
