package com.lframework.xingyun.template.gen.events;

import java.util.List;
import org.springframework.context.ApplicationEvent;

public class DataObjDeleteEvent extends ApplicationEvent {

  /**
   * 数据对象ID
   */
  private String id;

  /**
   * 数据对象名称
   */
  private String name;

  private List<String> detailIds;

  private List<String> queryDetailIds;

  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with which the event is
   *               associated (never {@code null})
   */
  public DataObjDeleteEvent(Object source) {
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

  public List<String> getDetailIds() {
    return detailIds;
  }

  public void setDetailIds(List<String> detailIds) {
    this.detailIds = detailIds;
  }

  public List<String> getQueryDetailIds() {
    return queryDetailIds;
  }

  public void setQueryDetailIds(List<String> queryDetailIds) {
    this.queryDetailIds = queryDetailIds;
  }
}
