package com.lframework.xingyun.sc.vo.retail.out;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryRetailOutSheetWithReturnVo extends PageVo {

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
}
