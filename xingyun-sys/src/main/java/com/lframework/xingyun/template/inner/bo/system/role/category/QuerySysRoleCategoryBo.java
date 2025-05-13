package com.lframework.xingyun.template.inner.bo.system.role.category;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.template.inner.entity.SysRoleCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuerySysRoleCategoryBo extends BaseBo<SysRoleCategory> {

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


  public QuerySysRoleCategoryBo() {

  }

  public QuerySysRoleCategoryBo(SysRoleCategory dto) {

    super(dto);
  }
}
