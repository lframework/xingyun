package com.lframework.xingyun.template.gen.components.magic;

import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.components.security.SecurityUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.components.security.CheckPermissionHandler;
import com.lframework.starter.web.components.security.PermissionCalcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ssssssss.magicapi.core.context.MagicUser;
import org.ssssssss.magicapi.core.exception.MagicLoginException;
import org.ssssssss.magicapi.core.interceptor.Authorization;
import org.ssssssss.magicapi.core.interceptor.AuthorizationInterceptor;
import org.ssssssss.magicapi.core.servlet.MagicHttpServletRequest;

/**
 * 自定义用户名密码登录
 */
@Component  //注入到Spring容器中
public class MagicCustomAuthorizationInterceptor implements AuthorizationInterceptor {

  @Autowired
  private CheckPermissionHandler checkPermissionHandler;

  /**
   * 配置是否需要登录
   */
  @Override
  public boolean requireLogin() {
    return false;
  }

  /**
   * 根据Token获取User
   */
  @Override
  public MagicUser getUserByToken(String token) throws MagicLoginException {
    AbstractUserDetails currentUser = SecurityUtil.getUserByToken(token);
    if (currentUser == null) {
      throw new MagicLoginException("请重新登录！");
    }

    return new MagicUser(currentUser.getId(), currentUser.getName(), token);
  }

  @Override
  public MagicUser login(String username, String password) throws MagicLoginException {
    throw new MagicLoginException("不支持手动登录，请先登录主系统！");
  }

  @Override
  public boolean allowVisit(MagicUser magicUser, MagicHttpServletRequest request,
      Authorization authorization) {

    String[] profiles = ApplicationUtil.getEnv().getActiveProfiles();
    if (ArrayUtil.isNotEmpty(profiles) && ArrayUtil.contains(profiles, "demo")) {
      return authorization == Authorization.VIEW;
    }
    return checkPermissionHandler.valid(PermissionCalcType.OR, "system:online-code:config");
  }
}
