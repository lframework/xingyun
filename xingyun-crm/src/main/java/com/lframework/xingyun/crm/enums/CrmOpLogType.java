package com.lframework.xingyun.crm.enums;

import com.lframework.starter.web.core.components.oplog.OpLogType;
import org.springframework.stereotype.Component;

/**
 * CRM 操作日志类型
 */
@Component
public class CrmOpLogType implements OpLogType {

  @Override
  public Integer getCode() {
    return 4000;
  }
}
