package com.lframework.xingyun.core.events.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

public class UpdateMemberEvent extends ApplicationEvent {

  /**
   * 会员ID
   */
  @Getter
  @Setter
  private String id;

  public UpdateMemberEvent(Object source) {
    super(source);
  }
}
