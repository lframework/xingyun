package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.RegUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.security.IUserTokenResolver;
import com.lframework.starter.web.components.security.SecurityConstants;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.SpelUtil;
import com.lframework.xingyun.core.annotations.OpLog;
import com.lframework.xingyun.core.enums.DefaultOpLogType;
import com.lframework.xingyun.core.utils.OpLogUtil;
import com.lframework.xingyun.template.inner.dto.MenuDto;
import com.lframework.xingyun.template.inner.entity.SysMenu;
import com.lframework.xingyun.template.inner.enums.system.SysMenuComponentType;
import com.lframework.xingyun.template.inner.enums.system.SysMenuDisplay;
import com.lframework.xingyun.template.inner.mappers.system.SysMenuMapper;
import com.lframework.xingyun.template.inner.service.system.SysMenuService;
import com.lframework.xingyun.template.inner.vo.system.menu.CreateSysMenuVo;
import com.lframework.xingyun.template.inner.vo.system.menu.SysMenuSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.menu.UpdateSysMenuVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author zmj
 * @since 2021-05-10
 */
@Service
public class SysMenuServiceImpl extends BaseMpServiceImpl<SysMenuMapper, SysMenu> implements
    SysMenuService {

  private String tokenKey = "X-Auth-Token";

  @Autowired
  private IUserTokenResolver userTokenResolver;

  @Override
  public List<SysMenu> queryList(List<Integer> moduleIds) {

    return this.doQuery(moduleIds);
  }

  @Override
  public List<SysMenu> getByRoleId(String roleId, List<Integer> moduleIds) {

    return this.doGetByRoleId(roleId, moduleIds);
  }

  @Cacheable(value = SysMenu.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysMenu findById(@NonNull String id) {

    return this.doGetById(id);
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "新增菜单，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(@NonNull CreateSysMenuVo vo) {

    SysMenu data = this.doCreate(vo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "修改菜单，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(@NonNull UpdateSysMenuVo vo) {

    SysMenu oriMenu = this.findById(vo.getId());

    if (!ObjectUtil.equals(vo.getDisplay(), oriMenu.getDisplay().getCode())) {
      throw new DefaultClientException("菜单【" + oriMenu.getTitle() + "】" + "不允许更改类型！");
    }

    SysMenu data = this.doUpdate(vo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "删除菜单，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(@NonNull String id) {

    SysMenu oriMenu = this.findById(id);

    List<SysMenu> children = this.doGetChildrenById(id);
    if (CollectionUtil.isNotEmpty(children)) {
      //如果子节点不为空
      throw new DefaultClientException("菜单【" + oriMenu.getTitle() + "】存在子菜单，无法删除！");
    }

    this.doDeleteById(id);
  }

  @Override
  public List<SysMenu> selector(SysMenuSelectorVo vo, List<Integer> moduleIds) {

    return this.doSelector(vo, moduleIds);
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "启用菜单，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void enable(@NonNull String id) {

    this.doEnable(id);
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "停用菜单，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void unable(@NonNull String id) {

    this.doUnable(id);
  }

  @Override
  public Boolean existPermission(String permission) {
    Wrapper<SysMenu> queryWrapper = Wrappers.lambdaQuery(SysMenu.class)
        .eq(SysMenu::getPermission, permission);
    return this.count(queryWrapper) > 0;
  }

  protected List<SysMenu> doQuery(List<Integer> moduleIds) {

    return getBaseMapper().query(moduleIds);
  }

  protected List<SysMenu> doGetByRoleId(String roleId, List<Integer> moduleIds) {

    return getBaseMapper().getByRoleId(roleId, moduleIds);
  }

  protected SysMenu doGetById(@NonNull String id) {

    return getBaseMapper().findById(id);
  }

  protected SysMenu doCreate(@NonNull CreateSysMenuVo vo) {

    SysMenu data = new SysMenu();

    data.setId(IdUtil.getId());
    this.setDataForCreate(vo, data);

    getBaseMapper().insert(data);

    return data;
  }

  protected SysMenu doUpdate(@NonNull UpdateSysMenuVo vo) {

    SysMenu data = new SysMenu();

    data.setId(vo.getId());

    this.setDataForCreate(vo, data);

    data.setAvailable(vo.getAvailable());

    SysMenuService thisService = getThis(this.getClass());

    SysMenu record = thisService.findById(vo.getId());

    data.setIsSpecial(record.getIsSpecial());

    getBaseMapper().deleteById(vo.getId());

    getBaseMapper().insert(data);

    return data;
  }

  protected void doDeleteById(@NonNull String id) {

    getBaseMapper().deleteById(id);
  }

  protected List<SysMenu> doSelector(SysMenuSelectorVo vo, List<Integer> moduleIds) {

    return getBaseMapper().selector(vo, moduleIds);
  }

  protected void doEnable(@NonNull String id) {

    List<String> ids = new ArrayList<>();
    // 启用时，需要将父级菜单也启用
    ids.add(id);

    String tmpId = id;
    while (!StringUtil.isBlank(tmpId)) {
      SysMenu sysMenu = getById(tmpId);
      if (sysMenu == null) {
        break;
      }
      if (StringUtil.isBlank(sysMenu.getParentId())) {
        break;
      }

      ids.add(sysMenu.getParentId());

      tmpId = sysMenu.getParentId();
    }
    Wrapper<SysMenu> wrapper = Wrappers.lambdaUpdate(SysMenu.class)
        .set(SysMenu::getAvailable, Boolean.TRUE).in(SysMenu::getId, ids);
    getBaseMapper().update(new SysMenu(), wrapper);
  }

  protected void doUnable(@NonNull String id) {

    Set<String> ids = new HashSet<>();
    // 停用时，需要将子级菜单也停用
    ids.add(id);
    while (true) {
      List<SysMenu> sysMenuList = list(Wrappers.lambdaQuery(SysMenu.class)
          .in(SysMenu::getParentId, ids));

      int oldSize = ids.size();
      if (CollectionUtil.isNotEmpty(sysMenuList)) {
        ids.addAll(sysMenuList.stream().map(SysMenu::getId).collect(Collectors.toList()));
      }
      if (oldSize == ids.size()) {
        break;
      }
    }

    Wrapper<SysMenu> wrapper = Wrappers.lambdaUpdate(SysMenu.class)
        .set(SysMenu::getAvailable, Boolean.FALSE).in(SysMenu::getId, ids);
    getBaseMapper().update(new SysMenu(), wrapper);
  }

  protected void setDataForCreate(@NonNull CreateSysMenuVo vo, @NonNull SysMenu data) {

    SysMenuDisplay sysMenuDisplay = EnumUtil.getByCode(SysMenuDisplay.class, vo.getDisplay());

    SysMenu parentMenu = null;
    if (!StringUtil.isBlank(vo.getParentId())) {
      parentMenu = this.findById(vo.getParentId());
      if (parentMenu == null) {
        throw new DefaultClientException("父级菜单不存在！");
      }
      if (parentMenu.getId().equals(data.getId())) {
        throw new DefaultClientException("父级菜单不能是当前菜单！");
      }
    }
    data.setCode(vo.getCode());
    data.setTitle(vo.getTitle());
    // fix 这里需要用null代替空字符串
    data.setParentId(parentMenu == null ? null : parentMenu.getId());
    data.setDisplay(sysMenuDisplay);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    if (sysMenuDisplay == SysMenuDisplay.CATALOG || sysMenuDisplay == SysMenuDisplay.FUNCTION) {
      if (parentMenu != null) {
        //父级菜单必须是目录
        if (parentMenu.getDisplay() != SysMenuDisplay.CATALOG) {
          throw new DefaultClientException(
              "父级菜单类型必须是【" + SysMenuDisplay.CATALOG.getDesc() + "】！");
        }
      }

      data.setName(vo.getName());
      data.setIcon(vo.getIcon());
      data.setPath(vo.getPath());
      data.setHidden(vo.getHidden());

      if (sysMenuDisplay == SysMenuDisplay.FUNCTION) {
        // 功能必须有parentId
        if (parentMenu == null) {
          throw new DefaultClientException(
              "此菜单类型是【" + SysMenuDisplay.FUNCTION.getDesc() + "】，父级菜单不能为空！");
        }
        data.setComponent(vo.getComponent());
        data.setComponentType(
            EnumUtil.getByCode(SysMenuComponentType.class, vo.getComponentType()));
        data.setNoCache(vo.getNoCache());

        if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(vo.getPermission())) {
          throw new DefaultClientException(
              "权限【" + SecurityConstants.PERMISSION_ADMIN_NAME + "】为内置权限，请修改！");
        }

        data.setPermission(vo.getPermission());
      }
    } else if (sysMenuDisplay == SysMenuDisplay.PERMISSION) {

      if (parentMenu != null) {
        //父级菜单必须是目录
        if (parentMenu.getDisplay() != SysMenuDisplay.FUNCTION) {
          throw new DefaultClientException(
              "父级菜单类型必须是【" + SysMenuDisplay.FUNCTION.getDesc() + "】！");
        }
      } else {
        throw new DefaultClientException(
            "此菜单类型是【" + SysMenuDisplay.PERMISSION.getDesc() + "】，父级菜单不能为空！");
      }

      if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(vo.getPermission())) {
        throw new DefaultClientException(
            "权限【" + SecurityConstants.PERMISSION_ADMIN_NAME + "】为内置权限，请修改！");
      }

      data.setPermission(vo.getPermission());
    }
  }

  protected List<SysMenu> doGetChildrenById(String id) {

    return getBaseMapper().getChildrenById(id);
  }

  @Override
  public List<MenuDto> getMenuByUserId(String userId, boolean isAdmin, List<Integer> moduleIds) {

    List<MenuDto> menus = this.doGetMenus(userId, isAdmin, moduleIds);

    List<String> collectionMenuIds = this.doGetCollectMenuIds(userId);

    if (!CollectionUtil.isEmpty(menus)) {
      // 用env渲染${xxx}属性
      menus.forEach(menu -> {
        menu.setPath(ApplicationUtil.resolvePlaceholders(menu.getPath()));
      });

      // 渲染spel表达式
      Map<String, Object> vars = getDefaultVars();
      menus.stream().filter(menu -> this.hasSpecExpression(menu.getPath())).forEach(menu -> {
        List<String> expressions = this.getAllExpressions(menu.getPath());
        if (!CollectionUtil.isEmpty(expressions)) {
          String oriPath = menu.getPath();
          for (String expression : expressions) {
            Object parsed = SpelUtil.parse(expression.replaceAll("\\{", "").replaceAll("}", ""),
                vars);
            oriPath = oriPath.replace(expression, parsed == null ? "" : String.valueOf(parsed));
          }

          menu.setPath(oriPath);
        }
      });

      if (!CollectionUtil.isEmpty(collectionMenuIds)) {
        menus.forEach(menu -> {
          menu.setIsCollect(collectionMenuIds.contains(menu.getId()));
        });
      }
    }

    return menus;
  }

  @Override
  public Set<String> getPermissionsByUserId(String userId, boolean isAdmin,
      List<Integer> moduleIds) {

    return getBaseMapper().getPermissionsByUserId(userId, isAdmin, moduleIds);
  }

  @Override
  public Set<String> getRolePermissionByUserId(String userId) {
    return getBaseMapper().getRolePermissionByUserId(userId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void collect(String userId, String menuId) {

    if (StringUtil.isBlank(userId) || StringUtil.isBlank(menuId)) {
      return;
    }

    this.cancelCollect(userId, menuId);

    getBaseMapper().collectMenu(IdUtil.getId(), userId, menuId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void cancelCollect(String userId, String menuId) {

    if (StringUtil.isBlank(userId) || StringUtil.isBlank(menuId)) {
      return;
    }

    getBaseMapper().cancelCollectMenu(userId, menuId);
  }

  @Override
  public List<String> getParentMenuIds(String menuId) {
    List<String> results = new ArrayList<>();

    String tmpMenuId = menuId;
    while (tmpMenuId != null) {
      SysMenu menu = getThis(this.getClass()).findById(tmpMenuId);
      if (menu != null && StringUtil.isNotBlank(menu.getParentId())) {
        results.add(menu.getParentId());
        tmpMenuId = menu.getParentId();
      } else {
        break;
      }
    }

    return results;
  }

  private List<String> getAllExpressions(String s) {

    if (!this.hasSpecExpression(s)) {
      return null;
    }

    List<String> results = new ArrayList<>();
    String[] arr = s.split("#\\{");
    for (int i = 1; i < arr.length; i++) {
      if (!arr[i].contains("}")) {
        continue;
      }
      results.add("#{" + arr[i].substring(0, arr[i].indexOf("}")) + "}");
    }

    return results;
  }

  private boolean hasSpecExpression(String s) {

    return RegUtil.isMatch(Pattern.compile("^.*#\\{.*}.*$"), s);
  }

  protected Map<String, Object> getDefaultVars() {

    Map<String, Object> vars = new HashMap<>();

    vars.put("_token", userTokenResolver.getToken());
    vars.put("_fullToken", userTokenResolver.getFullToken());
    vars.put("_tokenKey", tokenKey);

    return vars;
  }

  protected List<MenuDto> doGetMenus(String userId, boolean isAdmin, List<Integer> moduleIds) {

    return getBaseMapper().getMenuByUserId(userId, isAdmin, moduleIds);
  }

  protected List<String> doGetCollectMenuIds(String userId) {

    return getBaseMapper().getCollectMenuIds(userId);
  }

  @CacheEvict(value = SysMenu.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
