package com.lframework.xingyun.template.inner.service.system;

import com.lframework.xingyun.template.core.entity.SysDataPermissionData;
import com.lframework.starter.web.service.BaseMpService;
import java.util.List;

public interface SysDataPermissionDataService extends
    BaseMpService<SysDataPermissionData> {

  /**
   * 根据业务ID查询
   *
   * @param bizId
   * @return
   */
  List<SysDataPermissionData> getByBizId(String bizId);

  /**
   * 根据业务ID查询
   *
   * @param bizId
   * @param bizType
   * @return
   */
  SysDataPermissionData getByBizId(String bizId, Integer bizType, Integer permissionType);
}
