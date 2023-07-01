package com.lframework.xingyun.template.core.impl;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.core.dto.DeptDto;
import com.lframework.xingyun.template.core.mappers.DeptMapper;
import com.lframework.xingyun.template.core.service.DeptService;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author zmj
 */
@Service
public class DeptServiceImpl implements DeptService {

  @Autowired
  private DeptMapper deptMapper;

  @Cacheable(value = DeptDto.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public DeptDto findById(String id) {

    if (StringUtil.isBlank(id)) {
      return null;
    }

    return deptMapper.findById(id);
  }

  @Override
  public DeptDto findByCode(String code) {
    return deptMapper.findByCode(code);
  }

  @CacheEvict(value = {DeptDto.CACHE_NAME}, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
