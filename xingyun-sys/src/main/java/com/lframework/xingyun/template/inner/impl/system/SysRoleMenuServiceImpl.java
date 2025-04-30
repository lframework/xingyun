package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.web.components.security.SecurityConstants;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.core.annotations.OpLog;
import com.lframework.xingyun.core.enums.DefaultOpLogType;
import com.lframework.xingyun.template.inner.entity.SysMenu;
import com.lframework.xingyun.template.inner.entity.SysRole;
import com.lframework.xingyun.template.inner.entity.SysRoleMenu;
import com.lframework.xingyun.template.inner.enums.system.SysMenuDisplay;
import com.lframework.xingyun.template.inner.mappers.system.SysRoleMenuMapper;
import com.lframework.xingyun.template.inner.service.system.SysMenuService;
import com.lframework.xingyun.template.inner.service.system.SysRoleMenuService;
import com.lframework.xingyun.template.inner.service.system.SysRoleService;
import com.lframework.xingyun.template.inner.vo.system.role.SysRoleMenuSettingVo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysRoleMenuServiceImpl extends
    BaseMpServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

  @Autowired
  private SysRoleService sysRoleService;

  @Autowired
  private SysMenuService sysMenuService;

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "角色授权菜单，角色ID：{}，菜单ID：{}", params = {
      "#vo.roleIds",
      "#vo.menuIds"}, loopFormat = true)
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setting(SysRoleMenuSettingVo vo) {

    for (String roleId : vo.getRoleIds()) {
      SysRole role = sysRoleService.findById(roleId);
      if (ObjectUtil.isNull(role)) {
        throw new DefaultClientException("角色不存在！");
      }

      if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(role.getPermission())) {
        throw new DefaultClientException(
            "角色【" + role.getName() + "】的权限为【" + SecurityConstants.PERMISSION_ADMIN_NAME
                + "】，不允许授权！");
      }

      this.doSetting(roleId, vo.getMenuIds());
    }
  }

  protected void doSetting(String roleId, List<String> menuIds) {

    Wrapper<SysRoleMenu> deleteWrapper = Wrappers.lambdaQuery(SysRoleMenu.class)
        .eq(SysRoleMenu::getRoleId, roleId);
    getBaseMapper().delete(deleteWrapper);

    List<SysRoleMenu> records = new ArrayList<>();
    if (!CollectionUtil.isEmpty(menuIds)) {
      Set<String> menuIdSet = new HashSet<>(menuIds);

      // 根据所有的menuIds查询父级ID，父级ID需要添加
      for (String menuId : menuIds) {
        menuIdSet.addAll(sysMenuService.getParentMenuIds(menuId));
      }

      if (menuIdSet.stream().noneMatch(t -> {
        SysMenu menu = sysMenuService.findById(t);
        return menu != null && (menu.getDisplay() == SysMenuDisplay.FUNCTION || menu.getDisplay() == SysMenuDisplay.PERMISSION);
      })) {
        throw new DefaultClientException("授权失败，不能只选择类型是“" + SysMenuDisplay.CATALOG.getDesc() +  "”的菜单！");
      }
      for (String menuId : menuIdSet) {
        SysRoleMenu record = new SysRoleMenu();
        record.setId(IdUtil.getId());
        record.setRoleId(roleId);
        record.setMenuId(menuId);

        records.add(record);
      }
    }

    if (CollectionUtil.isNotEmpty(records)) {
      this.saveBatch(records);
    }
  }
}
