package com.lframework.xingyun.template.inner.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.EncryptUtil;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.inner.entity.Tenant;
import com.lframework.xingyun.template.inner.mappers.TenantMapper;
import com.lframework.xingyun.template.inner.service.TenantService;
import com.lframework.xingyun.template.inner.vo.system.tenant.CreateTenantVo;
import com.lframework.xingyun.template.inner.vo.system.tenant.QueryTenantVo;
import com.lframework.xingyun.template.inner.vo.system.tenant.TenantSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.tenant.UpdateTenantVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DS("master")
@Service
public class TenantServiceImpl extends BaseMpServiceImpl<TenantMapper, Tenant> implements
    TenantService {

  @Override
  public PageResult<Tenant> query(Integer pageIndex, Integer pageSize, QueryTenantVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<Tenant> datas = getBaseMapper().query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public PageResult<Tenant> selector(Integer pageIndex, Integer pageSize, TenantSelectorVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<Tenant> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = Tenant.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public Tenant findById(Integer id) {
    return getById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public Integer create(CreateTenantVo data) {

    Wrapper<Tenant> checkWrapper = Wrappers.lambdaQuery(Tenant.class)
        .eq(Tenant::getName, data.getName());
    if (count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }
    Tenant record = new Tenant();
    record.setName(data.getName());
    record.setJdbcUrl(data.getJdbcUrl());
    record.setJdbcUsername(data.getJdbcUsername());
    record.setJdbcPassword(EncryptUtil.encrypt(data.getJdbcPassword()));
    record.setAvailable(Boolean.TRUE);

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateTenantVo data) {

    Tenant record = this.getById(data.getId());
    if (record == null) {
      throw new DefaultClientException("租户不存在！");
    }

    Wrapper<Tenant> checkWrapper = Wrappers.lambdaQuery(Tenant.class)
        .eq(Tenant::getName, data.getName()).ne(Tenant::getId, data.getId());
    if (count(checkWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    LambdaUpdateWrapper<Tenant> updateWrapper = Wrappers.lambdaUpdate(Tenant.class)
        .eq(Tenant::getId, data.getId()).set(Tenant::getName, data.getName())
        .set(Tenant::getAvailable, data.getAvailable());
    if (StringUtil.isNotBlank(data.getJdbcUrl())) {
      updateWrapper.set(Tenant::getJdbcUrl, data.getJdbcUrl());
    }
    if (StringUtil.isNotBlank(data.getJdbcUsername())) {
      updateWrapper.set(Tenant::getJdbcUsername, data.getJdbcUsername());
    }
    if (StringUtil.isNotBlank(data.getJdbcPassword())) {
      updateWrapper.set(Tenant::getJdbcPassword, EncryptUtil.encrypt(data.getJdbcPassword()));
    }

    this.update(updateWrapper);
  }

  @CacheEvict(value = Tenant.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
