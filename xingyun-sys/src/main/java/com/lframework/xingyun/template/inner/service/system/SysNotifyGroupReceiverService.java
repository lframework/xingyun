package com.lframework.xingyun.template.inner.service.system;

import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.entity.SysNotifyGroupReceiver;
import java.util.List;

public interface SysNotifyGroupReceiverService extends BaseMpService<SysNotifyGroupReceiver> {

  /**
   * 根据组ID查询接收方ID
   *
   * @param groupId
   * @return
   */
  List<String> getReceiverIdsByGroupId(String groupId);
}
