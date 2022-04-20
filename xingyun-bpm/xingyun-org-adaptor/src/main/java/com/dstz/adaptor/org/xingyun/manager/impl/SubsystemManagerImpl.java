package com.dstz.adaptor.org.xingyun.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dstz.adaptor.org.xingyun.dao.SubsystemDao;
import com.dstz.adaptor.org.xingyun.entity.Subsystem;
import com.dstz.adaptor.org.xingyun.manager.SubsystemManager;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.org.api.model.IUser;
import com.dstz.sys.util.ContextUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 描述：子系统定义 处理实现类
 * </pre>
 */
@Service("subsystemManager")
public class SubsystemManagerImpl extends BaseManager<String, Subsystem> implements
    SubsystemManager {

  @Resource
  SubsystemDao subsystemDao;

  @Override
  public boolean isExist(Subsystem subsystem) {

    return subsystemDao.isExist(subsystem) > 0;
  }

  @Override
  public List<Subsystem> getList() {

    return subsystemDao.getList();
  }

  @Override
  public Subsystem getDefaultSystem(String userId) {

    List<Subsystem> list = subsystemDao.getSystemByUser(userId);
    if (CollectionUtil.isEmpty(list)) {
      return null;
    }

    for (Subsystem system : list) {
      if (1 == system.getIsDefault()) {
        return system;
      }
    }
    return list.get(0);
  }

  @Override
  public void setDefaultSystem(String systemId) {

    Subsystem subSystem = subsystemDao.get(systemId);
    if (subSystem.getIsDefault() == 1) {
      subSystem.setIsDefault(0);
    } else {
      subsystemDao.updNoDefault();
      subSystem.setIsDefault(1);
    }
    subsystemDao.update(subSystem);
  }


  @Override
  public List<Subsystem> getCuurentUserSystem() {

    IUser user = ContextUtil.getCurrentUser();
    if (ContextUtil.isAdmin(user)) {
      return subsystemDao.getList();
    }

    return subsystemDao.getSystemByUser(user.getUserId());
  }

  @Override
  public Subsystem getByAlias(String systemAlias) {

    return subsystemDao.getByAlias(systemAlias);
  }
}
