package com.lframework.xingyun.template.gen.vo.custom.form;

import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询自定义选择器Vo
 */
@Data
public class QueryGenCustomFormVo extends PageVo {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  private String categoryId;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;
}
