package com.lframework.xingyun.basedata.vo.member;

import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class QueryMemberSelectorVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;
}
