package com.lframework.xingyun.core.components.timeline;

import com.lframework.starter.web.core.components.timeline.OrderTimeLineBizType;
import org.springframework.stereotype.Component;

@Component
public class ReceiveOrderTimeLineBizType implements OrderTimeLineBizType {

  @Override
  public Integer getCode() {
    return 7;
  }
}
