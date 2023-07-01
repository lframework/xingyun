package com.lframework.xingyun.template.gen.events;

import org.springframework.context.ApplicationEvent;

public class CustomListDeleteEvent extends ApplicationEvent {

  /**
   * 自定义列表ID
   */
  private String id;

  /**
   * 自定义列表名称
   */
  private String name;

  public CustomListDeleteEvent(Object source) {
    super(source);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
