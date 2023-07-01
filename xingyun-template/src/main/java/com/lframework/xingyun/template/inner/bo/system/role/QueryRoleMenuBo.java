package com.lframework.xingyun.template.inner.bo.system.role;

import com.lframework.xingyun.template.inner.entity.SysMenu;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryRoleMenuBo extends BaseBo<SysMenu> {

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
   * 标题
   */
  @ApiModelProperty("标题")
  private String title;

  /**
   * 图标
   */
  @ApiModelProperty("图标")
  private String icon;

  /**
   * 类型
   */
  @ApiModelProperty("类型")
  private Integer display;

  /**
   * 父级ID
   */
  @ApiModelProperty("父级ID")
  private String parentId;

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
   * 是否选择
   */
  @ApiModelProperty("是否选择")
  private Boolean selected = Boolean.FALSE;

  public QueryRoleMenuBo() {

  }

  public QueryRoleMenuBo(SysMenu dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<SysMenu> convert(SysMenu dto) {

    return super.convert(dto, QueryRoleMenuBo::getDisplay);
  }

  @Override
  protected void afterInit(SysMenu dto) {

    this.display = dto.getDisplay().getCode();
  }
}
