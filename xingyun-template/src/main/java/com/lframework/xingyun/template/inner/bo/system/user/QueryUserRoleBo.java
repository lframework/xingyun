package com.lframework.xingyun.template.inner.bo.system.user;

import com.lframework.xingyun.template.inner.entity.SysRole;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryUserRoleBo extends BaseBo<SysRole> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

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

  /**
   * 权限
   */
  @ApiModelProperty("权限")
  private String permission;

  /**
   * 是否选中
   */
  @ApiModelProperty("是否选中")
  private Boolean selected = Boolean.FALSE;

  public QueryUserRoleBo() {

  }

  public QueryUserRoleBo(SysRole dto) {

    super(dto);
  }
}
