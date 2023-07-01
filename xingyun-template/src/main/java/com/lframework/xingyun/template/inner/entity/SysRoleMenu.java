package com.lframework.xingyun.template.inner.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-07-04
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 角色ID
   */
  private String roleId;

  /**
   * 菜单ID
   */
  private String menuId;


}
