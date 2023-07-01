package com.lframework.xingyun.template.inner.service.system;

import com.lframework.xingyun.template.inner.entity.SysNoticeLog;
import com.lframework.starter.web.service.BaseMpService;

/**
 * 系统通知记录 Service
 *
 * @author zmj
 */
public interface SysNoticeLogService extends BaseMpService<SysNoticeLog> {

  /**
   * 设置已读
   *
   * @param noticeId
   * @param userId
   */
  boolean setReaded(String noticeId, String userId);
}
