package com.lframework.xingyun.template.core.components.permission;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.common.security.SecurityConstants;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.template.core.service.SysDataPermissionModelDetailService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataPermissionHandler {

  private static final String EMPTY_SQL = "(1 = 1)";

  public static String getDataPermission(SysDataPermissionDataPermissionType permissionType,
      List<String> keys,
      List<String> values) {

    Map<String, String> dataPermissionMap = null;
    Map<String, String> dataPermissionVar = null;
    try {
      SaSession session = StpUtil.getSession(false);
      if (session == null) {
        return EMPTY_SQL;
      }
      dataPermissionMap = (Map<String, String>) session.get(
          SecurityConstants.DATA_PERMISSION_SQL_MAP);

      dataPermissionVar = (Map<String, String>) session.get(
          SecurityConstants.DATA_PERMISSION_SQL_VAR);
      if (CollectionUtil.isEmpty(dataPermissionMap)) {
        return EMPTY_SQL;
      }
    } catch (SaTokenException e) {
      return EMPTY_SQL;
    }

    Assert.notNull(permissionType);
    Assert.notNull(keys);
    Assert.notNull(values);

    String sqlTemplate = dataPermissionMap.get(permissionType.getCode().toString());
    if (StringUtil.isBlank(sqlTemplate)) {
      return EMPTY_SQL;
    }

    if (keys.size() != values.size()) {
      throw new DefaultSysException("keys和values长度不一致！");
    }

    Map<String, String> sqlParams = new HashMap<>();
    for (int i = 0; i < keys.size(); i++) {
      sqlParams.put(keys.get(i), values.get(i));
    }

    if (CollectionUtil.isNotEmpty(dataPermissionVar)) {
      dataPermissionVar.forEach((k, v) -> {
        sqlParams.put("__var#" + k, v);
      });
    }

    SysDataPermissionModelDetailService sysDataPermissionModelDetailService = ApplicationUtil.getBean(
        SysDataPermissionModelDetailService.class);
    return sysDataPermissionModelDetailService.formatSql(sqlTemplate, sqlParams);
  }
}
