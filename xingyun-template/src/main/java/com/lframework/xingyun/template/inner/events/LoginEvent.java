package com.lframework.xingyun.template.inner.events;

import com.lframework.starter.web.common.security.AbstractUserDetails;
import org.springframework.context.ApplicationEvent;

/**
 * 用户登录事件
 *
 * @author zmj
 */
public class LoginEvent extends ApplicationEvent {

  private final AbstractUserDetails user;

  private final String token;

  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with which the event is
   *               associated (never {@code null})
   */
  public LoginEvent(Object source, AbstractUserDetails user, String token) {

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
