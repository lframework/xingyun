package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.web.components.security.SecurityConstants;
import com.lframework.starter.web.components.security.SecurityUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.core.annotations.OpLog;
import com.lframework.xingyun.core.enums.DefaultOpLogType;
import com.lframework.xingyun.template.inner.entity.SysRole;
import com.lframework.xingyun.template.inner.entity.SysUser;
import com.lframework.xingyun.template.inner.entity.SysUserRole;
import com.lframework.xingyun.template.inner.mappers.system.SysUserRoleMapper;
import com.lframework.xingyun.template.inner.service.system.SysRoleService;
import com.lframework.xingyun.template.inner.service.system.SysUserRoleService;
import com.lframework.xingyun.template.inner.service.system.SysUserService;
import com.lframework.xingyun.template.inner.vo.system.user.SysUserRoleSettingVo;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserRoleServiceImpl extends
    BaseMpServiceImpl<SysUserRoleMapper, SysUserRole>
    implements SysUserRoleService {

  @Autowired
  private SysRoleService sysRoleService;

  @Autowired
  private SysUserService sysUserService;

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "用户授权角色，用户ID：{}，角色ID：{}", params = {
      "#vo.userIds",
      "#vo.roleIds"}, loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setting(SysUserRoleSettingVo vo) {

    for (String userId : vo.getUserIds()) {
      this.doSetting(userId, vo.getRoleIds());
    }
  }

  @Override
  public List<SysUserRole> getByUserId(String userId) {

    return doGetByUserId(userId);
  }

  protected void doSetting(String userId, List<String> roleIds) {

    Wrapper<SysUserRole> deleteWrapper = Wrappers.lambdaQuery(SysUserRole.class)
        .eq(SysUserRole::getUserId, userId);
    if (!SecurityUtil.getCurrentUser().isAdmin()) {
      List<SysUserRole> checkList = this.list(deleteWrapper);
      if (!CollectionUtil.isEmpty(checkList)) {
        List<SysRole> roleList = sysRoleService.listByIds(
            checkList.stream().map(SysUserRole::getRoleId)
                .collect(Collectors.toList()));
        if (roleList.stream()
            .anyMatch(t -> SecurityConstants.PERMISSION_ADMIN_NAME.equals(t.getPermission()))) {
          SysUser user = sysUserService.findById(userId);
          throw new DefaultClientException(
              "用户【" + user.getName() + "】的权限为管理员，非管理员用户无法为管理员用户授权！");
        }
      }
    }
    getBaseMapper().delete(deleteWrapper);

    if (!CollectionUtil.isEmpty(roleIds)) {
      Set<String> roleIdSet = new HashSet<>(roleIds);

      for (String roleId : roleIdSet) {
        SysRole role = sysRoleService.findById(roleId);
        if (ObjectUtil.isNull(role)) {
          throw new DefaultClientException("角色不存在，请检查！");
        }

        SysUserRole record = new SysUserRole();
        record.setId(IdUtil.getId());
        record.setUserId(userId);
        record.setRoleId(role.getId());

        getBaseMapper().insert(record);
      }
    }
  }

  protected List<SysUserRole> doGetByUserId(String userId) {

    return getBaseMapper().getByUserId(userId);
  }
}
