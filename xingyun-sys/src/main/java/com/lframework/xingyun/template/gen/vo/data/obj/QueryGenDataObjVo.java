package com.lframework.xingyun.template.gen.vo.data.obj;

import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询数据对象列表Vo
 */
@Data
public class QueryGenDataObjVo extends PageVo {

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
