package com.lframework.xingyun.template.inner.bo.system.role;

import com.lframework.xingyun.template.inner.entity.SysRole;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetSysRoleBo extends BaseBo<SysRole> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 岗位编号
   */
  @ApiModelProperty("岗位编号")
  private String code;

  /**
   * 岗位名称
   */
  @ApiModelProperty("岗位名称")
  private String name;

  /**
   * 权限
   */
  @ApiModelProperty("权限")
  private String permission;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  public GetSysRoleBo() {

  }

  public GetSysRoleBo(SysRole dto) {

    super(dto);
  }
}
