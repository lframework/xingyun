package com.lframework.xingyun.template.core.mappers;

import com.lframework.xingyun.template.core.dto.UserDto;

/**
 * 默认UserMapper
 *
 * @author zmj
 */
public interface UserMapper {

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
