package com.lframework.xingyun.template.inner.impl.system;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.utils.PageHelperUtil;
import com.lframework.starter.web.utils.PageResultUtil;
import com.lframework.xingyun.template.inner.entity.SysOpenDomain;
import com.lframework.xingyun.template.inner.mappers.system.SysOpenDomainMapper;
import com.lframework.xingyun.template.inner.service.system.SysOpenDomainService;
import com.lframework.xingyun.template.inner.vo.system.open.CreateSysOpenDomainVo;
import com.lframework.xingyun.template.inner.vo.system.open.QuerySysOpenDomainVo;
import com.lframework.xingyun.template.inner.vo.system.open.SysOpenDomainSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.open.UpdateSysOpenDomainSecretVo;
import com.lframework.xingyun.template.inner.vo.system.open.UpdateSysOpenDomainVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DS("master")
@Service
public class SysOpenDomainServiceImpl extends
    BaseMpServiceImpl<SysOpenDomainMapper, SysOpenDomain>
    implements SysOpenDomainService {

  @Override
  public PageResult<SysOpenDomain> query(QuerySysOpenDomainVo vo) {
    PageHelperUtil.startPage(vo);
    List<SysOpenDomain> datas = getBaseMapper().query(vo);
    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public PageResult<SysOpenDomain> selector(SysOpenDomainSelectorVo vo) {
    PageHelperUtil.startPage(vo);
    List<SysOpenDomain> datas = getBaseMapper().selector(vo);
    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = SysOpenDomain.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public SysOpenDomain findById(Integer id) {
    return this.getById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysOpenDomainVo vo) {
    SysOpenDomain data = new SysOpenDomain();
    data.setName(vo.getName());
    data.setApiSecret(vo.getApiSecret());
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    if (vo.getTenantId() != null) {
      data.setTenantId(vo.getTenantId());
    }

    this.save(data);

    return data.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysOpenDomainVo vo) {

    SysOpenDomain record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("开放域不存在！");
    }

    LambdaUpdateWrapper<SysOpenDomain> updateWrapper = Wrappers.lambdaUpdate(SysOpenDomain.class)
        .set(SysOpenDomain::getName, vo.getName())
        .set(SysOpenDomain::getAvailable, vo.getAvailable())
        .set(SysOpenDomain::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .set(SysOpenDomain::getTenantId, vo.getTenantId() == null ? null : vo.getTenantId())
        .eq(SysOpenDomain::getId, vo.getId());

    this.update(updateWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updateApiSecret(UpdateSysOpenDomainSecretVo vo) {
    SysOpenDomain record = this.getById(vo.getId());
    if (record == null) {
      throw new DefaultClientException("开放域不存在！");
    }

    LambdaUpdateWrapper<SysOpenDomain> updateWrapper = Wrappers.lambdaUpdate(SysOpenDomain.class)
        .set(SysOpenDomain::getApiSecret, vo.getApiSecret())
        .eq(SysOpenDomain::getId, vo.getId());

    this.update(updateWrapper);
  }

  @CacheEvict(value = SysOpenDomain.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
