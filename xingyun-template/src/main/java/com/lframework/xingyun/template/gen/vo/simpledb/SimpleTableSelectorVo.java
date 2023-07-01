package com.lframework.xingyun.template.gen.vo.simpledb;

import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimpleTableSelectorVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;
}
