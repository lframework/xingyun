package com.lframework.xingyun.template.inner.impl;

import com.lframework.xingyun.template.inner.mappers.UserDetailsMapper;
import com.lframework.starter.web.common.security.AbstractUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserDetaisService默认实现
 *
 * @author zmj
 */
@Service
public class UserDetailsService extends AbstractUserDetailsService {

  @Autowired
  private UserDetailsMapper userDetailsMapper;

  @Override
  public AbstractUserDetails findByUsername(String username) {

    AbstractUserDetails user = userDetailsMapper.findByUsername(username);

    return user;
  }
}
