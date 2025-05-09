package com.lframework.xingyun.basedata.vo.print;

import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.SortPageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryPrintTemplateVo extends SortPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;
}
