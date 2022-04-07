package com.dstz.adaptor.org.xingyun.dao;

import com.dstz.adaptor.org.xingyun.entity.Subsystem;
import com.dstz.base.dao.BaseDao;
import java.util.List;

/**
 * <pre>
 * 描述：子系统定义 DAO接口
 * </pre>
 */
public interface SubsystemDao extends BaseDao<String, Subsystem> {

  /**
   * 判断别名是否存在
   *
   * @param subsystem
   * @return
   */
  Integer isExist(Subsystem subsystem);

  /**
   * 获取子系统列表。
   *
   * @return
   */
  List<Subsystem> getList();

  /**
   * 更新为默认。
   */
  void updNoDefault();

  /**
   * 根据用户获取子系统列表。
   *
   * @param userId
   * @return
   */
  List<Subsystem> getSystemByUser(String userId);

  Subsystem getByAlias(String systemAlias);
}
