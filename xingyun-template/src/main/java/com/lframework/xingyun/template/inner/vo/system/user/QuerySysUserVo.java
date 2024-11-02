package com.lframework.xingyun.template.inner.vo.system.user;

import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.starter.web.vo.SortPageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class QuerySysUserVo extends SortPageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 用户名
   */
  @ApiModelProperty("用户名")
  private String username;

  /**
   * 姓名
   */
  @ApiModelProperty("姓名")
  private String name;

  /**
   * 部门ID
   */
  @ApiModelProperty("部门ID")
  private String deptId;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 是否锁定
   */
  @ApiModelProperty("是否锁定")
  private Boolean lockStatus;
}
