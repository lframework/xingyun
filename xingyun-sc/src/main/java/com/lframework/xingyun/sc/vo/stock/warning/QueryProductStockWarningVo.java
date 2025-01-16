package com.lframework.xingyun.sc.vo.stock.warning;

import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.SortPageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryProductStockWarningVo extends SortPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

  /**
   * 商品ID
   */
  @ApiModelProperty("商品ID")
  private String productId;

  /**
   * 操作时间 起始时间
   */
  @ApiModelProperty("操作时间 起始时间")
  @TypeMismatch(message = "操作时间起始时间格式有误！")
  private LocalDateTime updateTimeStart;

  /**
   * 操作时间 截止时间
   */
  @ApiModelProperty("操作时间 截止时间")
  @TypeMismatch(message = "操作时间截止时间格式有误！")
  private LocalDateTime updateTimeEnd;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  @TypeMismatch(message = "状态格式有误！")
  private Boolean available;

}
