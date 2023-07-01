package com.lframework.xingyun.template.inner.mappers.system;

import com.lframework.xingyun.template.inner.entity.SysRole;
import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.template.inner.vo.system.role.QuerySysRoleVo;
import com.lframework.xingyun.template.inner.vo.system.role.SysRoleSelectorVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-02
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SysRole> query(@Param("vo") QuerySysRoleVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysRole findById(String id);

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<SysRole> getByUserId(String userId);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<SysRole> selector(@Param("vo") SysRoleSelectorVo vo);
}
