package com.lframework.xingyun.template.core.service;

import com.lframework.xingyun.template.core.dto.UserDto;
import com.lframework.starter.web.service.BaseService;

/**
 * 用户Service
 *
 * @author zmj
 */
public interface UserService extends BaseService {

  /**
   * 根据ID查询 主要用于各个业务关联查询用户信息
   *
   * @param id
   * @return
   */
  UserDto findById(String id);

  /**
   * 根据编号查询
   *
   * @param code
   * @return
   */
  UserDto findByCode(String code);
}
