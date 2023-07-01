package com.lframework.xingyun.template.inner.impl;

import com.lframework.starter.common.exceptions.impl.UserLoginException;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.web.common.security.AbstractUserDetails;
import com.lframework.starter.web.common.security.SecurityConstants;
import com.lframework.starter.web.common.tenant.TenantContextHolder;
import com.lframework.starter.web.components.security.UserDetailsService;
import com.lframework.starter.web.utils.RequestUtil;
import com.lframework.starter.web.utils.TenantUtil;
import com.lframework.xingyun.template.inner.service.SysModuleTenantService;
import com.lframework.xingyun.template.inner.service.system.SysMenuService;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * 所有UserDetailsService都需要继承此类 如果需要更改用户表，那么继承此类注册Bean即可
 *
 * @author zmj
 */
@Slf4j
public abstract class AbstractUserDetailsService implements UserDetailsService {

  @Autowired
  private SysMenuService sysMenuService;

  @Autowired
  private SysModuleTenantService sysModuleTenantService;

  @Value("${remove-fixed-permissions:false}")
  private Boolean removeFixedPermission;

  @Override
  public AbstractUserDetails loadUserByUsername(String username) throws UserLoginException {

    //根据登录名查询
    AbstractUserDetails userDetails = this.findByUsername(username);

    if (ObjectUtil.isEmpty(userDetails)) {
      log.debug("用户名 {} 不存在", username);
      throw new UserLoginException("用户名或密码错误！");
    }

    userDetails.setTenantId(TenantContextHolder.getTenantId());

    //获取登录IP
    userDetails.setIp(RequestUtil.getRequestIp());

    // 先查询当前租户使用的module
    List<Integer> moduleIds = null;
    if (TenantUtil.enableTenant()) {
      moduleIds = sysModuleTenantService.getAvailableModuleIdsByTenantId(
          TenantContextHolder.getTenantId());
    }

    // 先取角色的权限
    userDetails.setPermissions(sysMenuService.getRolePermissionByUserId(userDetails.getId()));
    userDetails.setIsAdmin(
        userDetails.getPermissions().contains(SecurityConstants.PERMISSION_ADMIN_NAME));
    // 再取菜单的权限
    Set<String> permissions = sysMenuService.getPermissionsByUserId(userDetails.getId(),
        userDetails.isAdmin(), moduleIds);
    // 合并权限
    permissions.addAll(userDetails.getPermissions());
    if (this.removeFixedPermission) {
      permissions.remove(SecurityConstants.PERMISSION_ADMIN_NAME);
    }
    userDetails.setPermissions(permissions);

    return userDetails;
  }

  /**
   * 根据登录名查询
   *
   * @param username
   * @return
   */
  public abstract AbstractUserDetails findByUsername(String username);
}
