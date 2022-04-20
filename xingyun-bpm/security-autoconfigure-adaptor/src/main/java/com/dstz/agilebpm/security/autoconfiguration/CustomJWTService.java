package com.dstz.agilebpm.security.autoconfiguration;

import com.dstz.base.core.jwt.JWTService;
import com.dstz.base.core.util.StringUtil;
import com.lframework.web.common.security.AbstractUserDetails;
import com.lframework.web.common.security.SecurityUtil;

public class CustomJWTService extends JWTService {

  @Override
  public String getValidSubjectFromRedisToken(String authToken) {

    if (StringUtil.isEmpty(authToken)) {
      return null;
    }

    AbstractUserDetails user = SecurityUtil.getCurrentUser();

    return user == null ? null : user.getUsername();
  }

  @Override
  public void logoutRedisToken(String authToken) {

    throw new RuntimeException("集成模式不需要退出登录");
  }

  @Override
  public String generateToken(String username, String audience) {

    throw new RuntimeException("集成模式不能自主生成token");
  }
}
