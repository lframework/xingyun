package com.dstz.adaptor.org.xingyun.manager.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.dstz.adaptor.org.xingyun.dao.SysResourceDao;
import com.dstz.adaptor.org.xingyun.entity.SysResource;
import com.dstz.adaptor.org.xingyun.manager.SysResourceManager;
import com.dstz.base.manager.impl.BaseManager;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 描述：子系统资源 处理实现类
 * </pre>
 */
@Service("sysResourceManager")
public class SysResourceManagerImpl extends BaseManager<String, SysResource> implements
    SysResourceManager {

  @Resource
  SysResourceDao sysResourceDao;


  @Override
  public List<SysResource> getBySystemId(String id) {

    List<SysResource> list = sysResourceDao.getBySystemId(id);
    return list;
  }

  @Override
  public List<SysResource> getBySystemAndRole(String systemId, String roleId) {

    return sysResourceDao.getBySystemAndRole(systemId, roleId);
  }

  @Override
  public boolean isExist(SysResource resource) {

    boolean rtn = sysResourceDao.isExist(resource) > 0;
    return rtn;
  }

  @Override
  public void removeByResId(String resId) {

    SysResource resource = sysResourceDao.get(resId);
    if (resource == null) {
      return;
    }
    List<SysResource> relatedResouces = new ArrayList<>();

    getChildList(relatedResouces, resource.getId());
    for (SysResource r : relatedResouces) {
      this.remove(r.getId());
    }
    this.remove(resId);
  }


  private void getChildList(List<SysResource> relatedResouces, String id) {

    List<SysResource> children = sysResourceDao.getByParentId(id);
    if (CollectionUtil.isEmpty(children)) {
      return;
    }

    for (SysResource r : children) {
      getChildList(relatedResouces, r.getId());
    }
    relatedResouces.addAll(children);
  }

  @Override
  public List<SysResource> getBySystemAndUser(String systemId, String userId) {

    List<SysResource> list = sysResourceDao.getBySystemAndUser(systemId, userId);
    return list;
  }
}
