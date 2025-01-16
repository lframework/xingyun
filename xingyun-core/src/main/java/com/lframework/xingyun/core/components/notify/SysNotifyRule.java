package com.lframework.xingyun.core.components.notify;

/**
 * 消息通知规则
 */
public interface SysNotifyRule {

  /**
   * 根据业务类型匹配
   *
   * @param bizType
   * @return
   */
  boolean match(Integer bizType);
}
