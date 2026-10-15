package com.lframework.xingyun.crm.vo.opportunity;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryOpportunityVo extends PageVo {

  @Schema(description = "商机名称/客户名称关键字")
  private String keyword;

  @Schema(description = "状态")
  private Integer status;

  @Schema(description = "阶段")
  private Integer stage;

  @Schema(description = "负责销售员ID")
  private String ownerId;
}
