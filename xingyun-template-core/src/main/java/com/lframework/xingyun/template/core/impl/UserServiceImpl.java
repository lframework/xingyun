package com.lframework.xingyun.template.core.impl;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.xingyun.template.core.mappers.UserMapper;
import com.lframework.xingyun.template.core.service.UserService;
import com.lframework.xingyun.template.core.dto.UserDto;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 默认用户Service实现
 *
 * @author zmj
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;

  @Cacheable(value = UserDto.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public UserDto findById(String id) {

    if (StringUtil.isBlank(id)) {
      return null;
    }

    return userMapper.findById(id);
  }

  @Override
  public UserDto findByCode(String code) {
    return userMapper.findByCode(code);
  }

  @CacheEvict(value = {UserDto.CACHE_NAME}, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
