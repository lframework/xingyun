package com.lframework.xingyun.crm.vo.clue;

import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryClueVo extends PageVo {

  @Schema(description = "联系人姓名/公司名称关键字")
  private String keyword;

  @Schema(description = "线索状态")
  private Integer status;

  @Schema(description = "负责销售员ID")
  private String ownerId;
}
