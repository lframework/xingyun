package com.dstz.adaptor.org.xingyun.dao;

import com.dstz.adaptor.org.xingyun.entity.ResRole;
import com.dstz.base.dao.BaseDao;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <pre>
 * 描述：角色资源分配 DAO接口
 * </pre>
 */
public interface ResRoleDao extends BaseDao<String, ResRole> {

  List<ResRole> getByRoleId(String roleId);

  void removeByRoleAndSystem(@Param("roleId") String roleId, @Param("systemId") String systemId);

  /**
   * 获取资源和角色的映射关系
   *
   * @return
   */
  List<ResRole> getAllResRole();
}
