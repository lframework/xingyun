package com.dstz.adaptor.org.xingyun.manager.impl;

import com.dstz.adaptor.org.xingyun.dao.RoleDao;
import com.dstz.adaptor.org.xingyun.entity.Role;
import com.dstz.adaptor.org.xingyun.manager.RoleManager;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.manager.impl.BaseManager;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 描述：角色管理 处理实现类
 * </pre>
 */
@Service("roleManager")
public class RoleManagerImpl extends BaseManager<String, Role> implements RoleManager {

  @Resource
  RoleDao roleDao;

  public Role getByAlias(String alias) {
    return roleDao.getByAlias(alias);
  }

  @Override
  public List<Role> getByUserId(String userId) {
    if (StringUtil.isEmpty(userId)) {
      return Collections.emptyList();
    }
    return roleDao.getByUserId(userId);
  }

  @Override
  public boolean isRoleExist(Role role) {
    return roleDao.isRoleExist(role) != 0;
  }

}
