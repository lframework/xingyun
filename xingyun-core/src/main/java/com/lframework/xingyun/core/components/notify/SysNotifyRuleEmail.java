package com.lframework.xingyun.core.components.notify;

import com.lframework.xingyun.core.dto.notify.SysNotifyParamsDto;

/**
 * 消息通知规则（邮件）
 */
public interface SysNotifyRuleEmail extends SysNotifyRule {

  /**
   * 获取标题
   *
   * @param params
   * @return
   */
  String getTitle(SysNotifyParamsDto params);

  /**
   * 获取内容
   *
   * @param params
   * @return
   */
  String getContent(SysNotifyParamsDto params);
}
