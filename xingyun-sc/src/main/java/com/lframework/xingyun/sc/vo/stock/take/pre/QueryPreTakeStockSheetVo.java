package com.lframework.xingyun.sc.vo.stock.take.pre;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.SortPageVo;
import com.lframework.xingyun.sc.enums.PreTakeStockSheetStatus;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryPreTakeStockSheetVo extends SortPageVo implements BaseVo, Serializable {

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
   * 盘点状态
   */
  @ApiModelProperty("盘点状态")
  @TypeMismatch(message = "预先盘点状态格式有误！")
  @IsEnum(message = "预先盘点状态格式有误！", enumClass = PreTakeStockSheetStatus.class)
  private Integer takeStatus;

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

}
