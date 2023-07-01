package com.lframework.xingyun.template.inner.mappers;

import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.starter.web.common.security.DefaultUserDetails;

/**
 * 用于登录的用户信息查询Mapper
 *
 * @author zmj
 */
public interface UserDetailsMapper extends BaseMapper {

  /**
   * 根据登录名查询
   *
   * @param username
   * @return
   */
  DefaultUserDetails findByUsername(String username);
}
