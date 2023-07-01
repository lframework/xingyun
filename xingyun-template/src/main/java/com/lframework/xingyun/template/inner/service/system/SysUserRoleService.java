package com.lframework.xingyun.template.inner.service.system;

import com.lframework.xingyun.template.inner.entity.SysUserRole;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.vo.system.user.SysUserRoleSettingVo;
import java.util.List;

public interface SysUserRoleService extends BaseMpService<SysUserRole> {

  /**
   * 用户授权
   *
   * @param vo
   */
  void setting(SysUserRoleSettingVo vo);

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<SysUserRole> getByUserId(String userId);
}
