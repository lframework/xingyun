package com.lframework.xingyun.template.inner.events;

import org.springframework.context.ApplicationEvent;

/**
 * 修改用户事件
 *
 * @author zmj
 */
public class UpdateUserEvent extends ApplicationEvent {

  /**
   * 用户ID
   */
  private String id;

  public UpdateUserEvent(Object source, String id) {

    super(source);
    this.id = id;
  }

  public String getId() {

    return id;
  }
}
