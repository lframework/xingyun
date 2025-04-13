package com.lframework.xingyun.export.vo;

import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryFailExportTaskVo extends PageVo {

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;
}
