package com.lframework.xingyun.produce.vo.order;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryProductionOrderVo extends PageVo {

  @Schema(description = "工单编号/成品名称关键字")
  private String keyword;

  @Schema(description = "状态")
  private Integer status;

  @Schema(description = "计划开始日期 起")
  private String planStartDateStart;

  @Schema(description = "计划开始日期 止")
  private String planStartDateEnd;

  @Schema(description = "负责人ID")
  private String ownerId;
}
