package com.lframework.xingyun.template.inner.vo.system.user;

import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class SysUserSelectorVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 姓名
   */
  @ApiModelProperty("姓名")
  private String name;

  /**
   * 用户名
   */
  @ApiModelProperty("用户名")
  private String username;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;
}
