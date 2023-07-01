package com.lframework.xingyun.core.components.permission;

import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.core.components.permission.impl.OrderDataPermissionType;
import com.lframework.xingyun.core.components.permission.impl.ProductDataPermissionType;
import com.lframework.xingyun.template.core.components.permission.SysDataPermissionDataPermissionType;

public interface DataPermissionPool {

  /**
   * 商品数据权限
   */
  SysDataPermissionDataPermissionType PRODUCT = ApplicationUtil.getBean(
      ProductDataPermissionType.class);

  /**
   * 单据数据权限
   */
  SysDataPermissionDataPermissionType ORDER = ApplicationUtil.getBean(
      OrderDataPermissionType.class);
}
