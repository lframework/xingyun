package com.lframework.xingyun.template.gen.events;

import java.util.List;
import org.springframework.context.ApplicationEvent;

public class DataEntityDeleteEvent extends ApplicationEvent {

  /**
   * 数据实体ID
   */
  private String id;

  /**
   * 数据实体名称
   */
  private String name;

  private List<String> columnIds;

  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with which the event is
   *               associated (never {@code null})
   */
  public DataEntityDeleteEvent(Object source) {

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

  public List<String> getColumnIds() {

    return columnIds;
  }

  public void setColumnIds(List<String> columnIds) {

    this.columnIds = columnIds;
  }
}
