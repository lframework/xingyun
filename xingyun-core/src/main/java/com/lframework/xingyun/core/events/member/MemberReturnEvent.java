package com.lframework.xingyun.core.events.member;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

public class MemberReturnEvent extends ApplicationEvent {

  /**
   * 会员ID
   */
  @Getter
  @Setter
  private String id;

  /**
   * 退货金额
   */
  @Getter
  @Setter
  private BigDecimal amount;

  public MemberReturnEvent(Object source) {
    super(source);
  }
}
