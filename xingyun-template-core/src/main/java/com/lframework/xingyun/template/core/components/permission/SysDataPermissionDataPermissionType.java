package com.lframework.xingyun.template.core.components.permission;

import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import java.util.Map;

public interface SysDataPermissionDataPermissionType {

  Integer getCode();

  static SysDataPermissionDataPermissionType[] values() {
    Map<String, SysDataPermissionDataPermissionType> permissionTypeMap = ApplicationUtil.getBeansOfType(
        SysDataPermissionDataPermissionType.class);
    if (CollectionUtil.isEmpty(permissionTypeMap)) {
      return new SysDataPermissionDataPermissionType[0];
    }

    return ArrayUtil.toArray(permissionTypeMap.values(), SysDataPermissionDataPermissionType.class);
  }
}
