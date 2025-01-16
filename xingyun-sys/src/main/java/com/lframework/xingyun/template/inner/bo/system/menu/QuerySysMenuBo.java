package com.lframework.xingyun.template.inner.bo.system.menu;

import com.lframework.xingyun.template.inner.entity.SysMenu;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuerySysMenuBo extends BaseBo<SysMenu> {

  private static final long serialVersionUID = 1L;

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
   * 父级ID
   */
  @ApiModelProperty("父级ID")
  private String parentId;

  /**
   * 类型
   */
  @ApiModelProperty("类型")
  private Integer display;

  /**
   * 权限
   */
  @ApiModelProperty("权限")
  private String permission;

  /**
   * 是否特殊菜单
   */
  @ApiModelProperty("是否特殊菜单")
  private Boolean isSpecial;

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

  public QuerySysMenuBo() {

  }

  public QuerySysMenuBo(SysMenu dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<SysMenu> convert(SysMenu dto) {

    return super.convert(dto, QuerySysMenuBo::getDisplay);
  }

  @Override
  protected void afterInit(SysMenu dto) {

    this.display = dto.getDisplay().getCode();
  }
}
