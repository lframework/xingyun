package com.dstz.adaptor.org.xingyun.dao;

import com.dstz.adaptor.org.xingyun.entity.Role;
import com.dstz.base.dao.BaseDao;
import java.util.List;

/**
 * 描述：角色管理 DAO接口
 */
public interface RoleDao extends BaseDao<String, Role> {

  Role getByAlias(String alias);

  /**
   * 判断角色系统中是否存在。
   *
   * @param role
   * @return
   */
  Integer isRoleExist(Role role);

  /**
   * 用过用户ID 获取角色
   *
   * @param userId
   * @return
   */
  List<Role> getByUserId(String userId);
}
