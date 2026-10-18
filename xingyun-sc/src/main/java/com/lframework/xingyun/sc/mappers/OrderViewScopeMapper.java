package com.lframework.xingyun.sc.mappers;

import java.util.List;
import com.lframework.starter.web.core.annotations.permission.DataPermission;
import com.lframework.starter.web.core.annotations.permission.DataPermissions;
import com.lframework.starter.web.inner.components.permission.OrderDataPermissionDataPermissionType;
import org.apache.ibatis.annotations.Param;

public interface OrderViewScopeMapper {

  List<String> getRoleScopes(@Param("userId") String userId,
      @Param("permissionType") int permissionType);

  @DataPermissions(type = OrderDataPermissionDataPermissionType.class, value = {
      @DataPermission(template = "order", alias = "o")
  })
  int countReadablePurchase(@Param("id") String id, @Param("isForm") boolean isForm);

  @DataPermissions(type = OrderDataPermissionDataPermissionType.class, value = {
      @DataPermission(template = "order", alias = "o")
  })
  int countReadableSale(@Param("id") String id);
}
