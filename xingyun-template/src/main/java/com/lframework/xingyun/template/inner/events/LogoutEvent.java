package com.lframework.xingyun.template.inner.events;

import com.lframework.starter.web.common.security.AbstractUserDetails;
import org.springframework.context.ApplicationEvent;

/**
 * 用户退出登录事件
 *
 * @author zmj
 */
public class LogoutEvent extends ApplicationEvent {

  private final AbstractUserDetails user;

  private final String token;

  public LogoutEvent(Object source, AbstractUserDetails user, String token) {

    super(source);
    this.user = user;
    this.token = token;
  }

  public AbstractUserDetails getUser() {

    return user;
  }

  public String getToken() {

    return token;
  }
}
