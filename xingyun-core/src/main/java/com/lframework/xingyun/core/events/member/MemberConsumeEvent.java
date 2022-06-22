package com.lframework.xingyun.core.events.member;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

public class MemberConsumeEvent extends ApplicationEvent {

  /**
   * 会员ID
   */
  @Getter
  @Setter
  private String id;

  /**
   * 消费金额
   */
  @Getter
  @Setter
  private BigDecimal amount;

  public MemberConsumeEvent(Object source) {
    super(source);
  }
}
