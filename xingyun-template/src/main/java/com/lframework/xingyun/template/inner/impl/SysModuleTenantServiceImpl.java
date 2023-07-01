package com.lframework.xingyun.template.inner.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.web.impl.BaseMpServiceImpl;
import com.lframework.xingyun.template.inner.entity.SysModuleTenant;
import com.lframework.xingyun.template.inner.mappers.SysModuleTenantMapper;
import com.lframework.xingyun.template.inner.service.SysModuleTenantService;
import com.lframework.xingyun.template.inner.vo.system.module.SysModuleTenantVo;
import com.lframework.starter.web.utils.IdUtil;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DS("master")
@Service
public class SysModuleTenantServiceImpl extends
    BaseMpServiceImpl<SysModuleTenantMapper, SysModuleTenant> implements SysModuleTenantService {

  @Override
  public List<Integer> getAvailableModuleIdsByTenantId(Integer tenantId) {
    SysModuleTenantService thisService = getThis(getClass());
    return thisService.getByTenantId(tenantId).stream()
        .filter(t -> DateUtil.now().isBefore(t.getExpireTime())).map(SysModuleTenant::getModuleId)
        .collect(Collectors.toList());
  }

  @Cacheable(value = SysModuleTenant.CACHE_NAME, key = "#tenantId", unless = "#result == null or #result.isEmpty()")
  @Override
  public List<SysModuleTenant> getByTenantId(Integer tenantId) {
    Wrapper<SysModuleTenant> queryWrapper = Wrappers.lambdaQuery(SysModuleTenant.class)
        .eq(SysModuleTenant::getTenantId, tenantId);
    return this.list(queryWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setting(SysModuleTenantVo vo) {
    Wrapper<SysModuleTenant> queryWrapper = Wrappers.lambdaQuery(SysModuleTenant.class)
        .eq(SysModuleTenant::getTenantId, vo.getTenantId());
    this.remove(queryWrapper);

    if (CollectionUtil.isNotEmpty(vo.getModules())) {
      List<SysModuleTenant> records = vo.getModules().stream().map(t -> {
        SysModuleTenant record = new SysModuleTenant();
        record.setId(IdUtil.getId());
        record.setModuleId(t.getModuleId());
        record.setTenantId(vo.getTenantId());
        record.setExpireTime(t.getExpireTime());

        return record;
      }).collect(Collectors.toList());

      this.saveBatch(records);
    }
  }

  @CacheEvict(value = SysModuleTenant.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
