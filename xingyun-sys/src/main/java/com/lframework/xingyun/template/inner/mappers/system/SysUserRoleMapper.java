package com.lframework.xingyun.template.inner.mappers.system;

import com.lframework.xingyun.template.inner.entity.SysUserRole;
import com.lframework.starter.web.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-04
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<SysUserRole> getByUserId(String userId);
}
