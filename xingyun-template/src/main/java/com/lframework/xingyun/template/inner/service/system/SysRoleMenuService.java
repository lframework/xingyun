package com.lframework.xingyun.template.inner.service.system;

import com.lframework.xingyun.template.inner.entity.SysRoleMenu;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.vo.system.role.SysRoleMenuSettingVo;

public interface SysRoleMenuService extends BaseMpService<SysRoleMenu> {

  /**
   * 授权角色菜单
   *
   * @param vo
   */
  void setting(SysRoleMenuSettingVo vo);
}
