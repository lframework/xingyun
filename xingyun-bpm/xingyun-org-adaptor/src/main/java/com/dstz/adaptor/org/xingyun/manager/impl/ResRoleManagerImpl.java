package com.dstz.adaptor.org.xingyun.manager.impl;

import com.dstz.adaptor.org.xingyun.dao.ResRoleDao;
import com.dstz.adaptor.org.xingyun.entity.ResRole;
import com.dstz.adaptor.org.xingyun.manager.ResRoleManager;
import com.dstz.base.core.cache.ICache;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.manager.impl.BaseManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 描述：角色资源分配 处理实现类
 * </pre>
 */
@Service("resRoleManager")
public class ResRoleManagerImpl extends BaseManager<String, ResRole> implements ResRoleManager {

  @Resource
  ResRoleDao resRoleDao;
  @Resource
  ICache iCache;
  public final String URL_ROLE_MAPPING = "agilebpm:sys:resoucesUrlRoleMapping:";

  @Override
  public List<ResRole> getAllByRoleId(String roleId) {
    return resRoleDao.getByRoleId(roleId);
  }


  @Override
  public void assignResByRoleSys(String resIds, String systemId, String roleId) {
    resRoleDao.removeByRoleAndSystem(roleId, systemId);

    String[] aryRes = resIds.split(",");
    for (String resId : aryRes) {
      if ("0".equals(resId)) {
        continue;
      }
      ResRole resRole = new ResRole(systemId, resId, roleId);
      resRoleDao.create(resRole);
    }
    cleanResoucesCache();
  }

  private Map<String, Set<String>> getUrlRoleMapping() {
    if (iCache.containKey(URL_ROLE_MAPPING)) {
      return (Map<String, Set<String>>) iCache.getByKey(URL_ROLE_MAPPING);
    }

    List<ResRole> list = resRoleDao.getAllResRole();
    Map<String, Set<String>> urlRoleMapping = new HashMap<String, Set<String>>();

    for (ResRole res : list) {
      String url = res.getUrl();
      if (StringUtil.isEmpty(url)) {
        continue;
      }

      if (urlRoleMapping.containsKey(url)) {
        Set<String> set = urlRoleMapping.get(url);
        set.add(res.getRoleAlias());
      } else {
        Set<String> set = new HashSet<String>();
        set.add(res.getRoleAlias());
        urlRoleMapping.put(url, set);
      }
    }
    //添加到缓存
    iCache.add(URL_ROLE_MAPPING, urlRoleMapping);
    return urlRoleMapping;
  }

  private void cleanResoucesCache() {
    iCache.delByKey(URL_ROLE_MAPPING);
  }


  /**
   * TODO 将 url accessRoleUrl 放进 set 结构的redis缓存中
   */
  @Override
  public Set<String> getAccessRoleByUrl(String url) {
    url = url.trim();
    if (StringUtil.isEmpty(url)) {
      return Collections.emptySet();
    }

    Map<String, Set<String>> urlMapping = getUrlRoleMapping();
    Set<String> urlAccessRoles = urlMapping.get(url);
    return urlAccessRoles;
  }


}
