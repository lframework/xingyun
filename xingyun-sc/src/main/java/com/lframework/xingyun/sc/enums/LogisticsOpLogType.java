package com.lframework.xingyun.sc.enums;

import com.lframework.starter.web.core.components.oplog.OpLogType;
import org.springframework.stereotype.Component;

@Component
public class LogisticsOpLogType implements OpLogType {

  @Override
  public Integer getCode() {
    return 2000;
  }
}
