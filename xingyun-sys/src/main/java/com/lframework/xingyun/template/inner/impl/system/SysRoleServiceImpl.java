package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.security.SecurityConstants;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.core.annotations.OpLog;
import com.lframework.xingyun.core.enums.DefaultOpLogType;
import com.lframework.xingyun.core.utils.OpLogUtil;
import com.lframework.xingyun.template.inner.entity.SysRole;
import com.lframework.xingyun.template.inner.mappers.system.SysRoleMapper;
import com.lframework.xingyun.template.inner.service.system.SysMenuService;
import com.lframework.xingyun.template.inner.service.system.SysRoleService;
import com.lframework.xingyun.template.inner.vo.system.role.CreateSysRoleVo;
import com.lframework.xingyun.template.inner.vo.system.role.QuerySysRoleVo;
import com.lframework.xingyun.template.inner.vo.system.role.SysRoleSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.role.UpdateSysRoleVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysRoleServiceImpl extends BaseMpServiceImpl<SysRoleMapper, SysRole> implements
    SysRoleService {

  @Autowired
  private SysMenuService sysMenuService;

  @Override
  public PageResult<SysRole> query(Integer pageIndex, Integer pageSize,
      QuerySysRoleVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysRole> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysRole> query(QuerySysRoleVo vo) {

    return this.doQuery(vo);
  }

  @Cacheable(value = SysRole.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysRole findById(String id) {

    return this.doGetById(id);
  }

  @Override
  public PageResult<SysRole> selector(Integer pageIndex, Integer pageSize,
      SysRoleSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysRole> datas = this.doSelector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "停用角色，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void unable(String id) {

    SysRole role = this.findById(id);
    if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(role.getPermission())) {
      throw new DefaultClientException(
          "角色【" + role.getName() + "】的权限为【" + SecurityConstants.PERMISSION_ADMIN_NAME
              + "】，不允许停用！");
    }

    this.doUnable(id);
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "启用角色，ID：{}", params = "#id")
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void enable(String id) {

    SysRole role = this.findById(id);
    if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(role.getPermission())) {
      throw new DefaultClientException(
          "角色【" + role.getName() + "】的权限为【" + SecurityConstants.PERMISSION_ADMIN_NAME
              + "】，不允许启用！");
    }

    this.doEnable(id);
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "新增角色，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysRoleVo vo) {

    if (!StringUtil.isBlank(vo.getPermission())) {

      if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(vo.getPermission())) {
        throw new DefaultClientException(
            "权限【" + SecurityConstants.PERMISSION_ADMIN_NAME + "】为内置权限，请修改！");
      }

      // 这里的权限不能与菜单权限重复
      if (sysMenuService.existPermission(vo.getPermission())) {
        throw new DefaultClientException(
            "权限【" + vo.getPermission() + "】为菜单权限，请修改！");
      }
    }

    SysRole data = this.doCreate(vo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.SYSTEM, name = "修改角色，ID：{}, 编号：{}", params = {"#id",
      "#code"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysRoleVo vo) {

    SysRole data = this.findById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("角色不存在！");
    }

    if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(data.getPermission())) {
      throw new DefaultClientException("角色【" + data.getName() + "】为内置角色，不允许修改！");
    }

    if (!StringUtil.isBlank(vo.getPermission())) {

      if (SecurityConstants.PERMISSION_ADMIN_NAME.equals(vo.getPermission())) {
        throw new DefaultClientException(
            "权限【" + SecurityConstants.PERMISSION_ADMIN_NAME + "】为内置权限，请修改！");
      }

      // 这里的权限不能与菜单权限重复
      if (sysMenuService.existPermission(vo.getPermission())) {
        throw new DefaultClientException(
            "权限【" + vo.getPermission() + "】为菜单权限，请修改！");
      }
    }

    this.doUpdate(vo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public List<SysRole> getByUserId(String userId) {

    return this.doGetByUserId(userId);
  }

  protected List<SysRole> doQuery(QuerySysRoleVo vo) {

    return getBaseMapper().query(vo);
  }

  protected SysRole doGetById(String id) {

    return getBaseMapper().findById(id);
  }

  protected List<SysRole> doSelector(SysRoleSelectorVo vo) {

    return getBaseMapper().selector(vo);
  }

  protected void doUnable(String id) {

    Wrapper<SysRole> updateWrapper = Wrappers.lambdaUpdate(SysRole.class)
        .set(SysRole::getAvailable, Boolean.FALSE).eq(SysRole::getId, id);
    getBaseMapper().update(updateWrapper);
  }

  protected void doEnable(String id) {

    Wrapper<SysRole> updateWrapper = Wrappers.lambdaUpdate(SysRole.class)
        .set(SysRole::getAvailable, Boolean.TRUE).eq(SysRole::getId, id);
    getBaseMapper().update(updateWrapper);
  }

  protected SysRole doCreate(CreateSysRoleVo vo) {

    Wrapper<SysRole> checkWrapper = Wrappers.lambdaQuery(SysRole.class)
        .eq(SysRole::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysRole.class)
        .eq(SysRole::getName, vo.getName());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    SysRole data = new SysRole();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setCategoryId(vo.getCategoryId());

    if (!StringUtil.isBlank(vo.getPermission())) {

      data.setPermission(vo.getPermission());
    }

    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(data);

    return data;
  }

  protected void doUpdate(UpdateSysRoleVo vo) {

    Wrapper<SysRole> checkWrapper = Wrappers.lambdaQuery(SysRole.class)
        .eq(SysRole::getCode, vo.getCode()).ne(SysRole::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysRole.class)
        .eq(SysRole::getName, vo.getName())
        .ne(SysRole::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    LambdaUpdateWrapper<SysRole> updateWrapper = Wrappers.lambdaUpdate(SysRole.class)
        .set(SysRole::getCode, vo.getCode()).set(SysRole::getName, vo.getName())
        .set(SysRole::getPermission, null)
        .set(SysRole::getCategoryId, vo.getCategoryId())
        .set(SysRole::getAvailable, vo.getAvailable())
        .set(SysRole::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(SysRole::getId, vo.getId());

    if (!StringUtil.isBlank(vo.getPermission())) {

      updateWrapper.set(SysRole::getPermission, vo.getPermission());
    }

    getBaseMapper().update(updateWrapper);
  }

  protected List<SysRole> doGetByUserId(String userId) {

    return getBaseMapper().getByUserId(userId);
  }

  @CacheEvict(value = SysRole.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
