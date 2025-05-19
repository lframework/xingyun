package com.lframework.xingyun.template.inner.service.system;

import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.entity.SysUserGroupDetail;
import java.util.List;

public interface SysUserGroupDetailService extends BaseMpService<SysUserGroupDetail> {

  /**
   * 根据组ID查询用户ID
   *
   * @param groupId
   * @return
   */
  List<String> getUserIdsByGroupId(String groupId);
}
