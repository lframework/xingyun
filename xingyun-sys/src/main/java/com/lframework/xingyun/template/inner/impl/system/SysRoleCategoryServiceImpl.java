package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.inner.entity.SysRole;
import com.lframework.xingyun.template.inner.entity.SysRoleCategory;
import com.lframework.xingyun.template.inner.mappers.system.SysRoleCategoryMapper;
import com.lframework.xingyun.template.inner.service.system.SysRoleCategoryService;
import com.lframework.xingyun.template.inner.service.system.SysRoleService;
import com.lframework.xingyun.template.inner.vo.system.role.category.CreateSysRoleCategoryVo;
import com.lframework.xingyun.template.inner.vo.system.role.category.SysRoleCategorySelectorVo;
import com.lframework.xingyun.template.inner.vo.system.role.category.UpdateSysRoleCategoryVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysRoleCategoryServiceImpl extends
    BaseMpServiceImpl<SysRoleCategoryMapper, SysRoleCategory> implements
    SysRoleCategoryService {

  @Autowired
  private SysRoleService sysRoleService;

  @Cacheable(value = SysRoleCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + 'all'")
  @Override
  public List<SysRoleCategory> queryList() {
    return getBaseMapper().query();
  }

  @Override
  public PageResult<SysRoleCategory> selector(Integer pageIndex, Integer pageSize,
      SysRoleCategorySelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysRoleCategory> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = SysRoleCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysRoleCategory findById(String id) {
    return getBaseMapper().selectById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysRoleCategoryVo vo) {

    Wrapper<SysRoleCategory> checkWrapper = Wrappers.lambdaQuery(SysRoleCategory.class)
        .eq(SysRoleCategory::getCode, vo.getCode());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysRoleCategory.class)
        .eq(SysRoleCategory::getName, vo.getName());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    SysRoleCategory record = new SysRoleCategory();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysRoleCategoryVo vo) {
    Wrapper<SysRoleCategory> checkWrapper = Wrappers.lambdaQuery(SysRoleCategory.class)
        .eq(SysRoleCategory::getCode, vo.getCode())
        .ne(SysRoleCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(SysRoleCategory.class)
        .eq(SysRoleCategory::getName, vo.getName())
        .ne(SysRoleCategory::getId, vo.getId());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    SysRoleCategory record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("角色分类不存在！");
    }

    record.setCode(vo.getCode());
    record.setName(vo.getName());

    this.updateById(record);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(String id) {

    Wrapper<SysRole> queryWrapper = Wrappers.lambdaQuery(SysRole.class)
        .eq(SysRole::getCategoryId, id);
    if (sysRoleService.count(queryWrapper) > 0) {
      throw new DefaultClientException("此分类下存在角色，无法删除！");
    }

    this.removeById(id);
  }

  @CacheEvict(value = SysRoleCategory.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {
  }
}
