package com.lframework.xingyun.sc.vo.purchase.requisition;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryPurchaseRequisitionVo extends PageVo {

  @Schema(description = "单号/关键字")
  private String keyword;

  @Schema(description = "状态")
  private Integer status;

  @Schema(description = "来源生产工单ID")
  private String productionOrderId;

  @Schema(description = "创建日期 起")
  private String createDateStart;

  @Schema(description = "创建日期 止")
  private String createDateEnd;
}
