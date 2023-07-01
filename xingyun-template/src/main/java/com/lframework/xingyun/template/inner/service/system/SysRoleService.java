package com.lframework.xingyun.template.inner.service.system;

import com.lframework.xingyun.template.inner.entity.SysRole;
import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.vo.system.role.CreateSysRoleVo;
import com.lframework.xingyun.template.inner.vo.system.role.QuerySysRoleVo;
import com.lframework.xingyun.template.inner.vo.system.role.SysRoleSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.role.UpdateSysRoleVo;
import java.util.Collection;
import java.util.List;

public interface SysRoleService extends BaseMpService<SysRole> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<SysRole> query(Integer pageIndex, Integer pageSize, QuerySysRoleVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SysRole> query(QuerySysRoleVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysRole findById(String id);

  /**
   * 选择器
   *
   * @return
   */
  PageResult<SysRole> selector(Integer pageIndex, Integer pageSize, SysRoleSelectorVo vo);

  /**
   * 根据ID停用
   *
   * @param ids
   */
  void batchUnable(Collection<String> ids);

  /**
   * 根据ID启用
   *
   * @param ids
   */
  void batchEnable(Collection<String> ids);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysRoleVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysRoleVo vo);

  /**
   * 根据用户ID查询
   *
   * @param userId
   * @return
   */
  List<SysRole> getByUserId(String userId);
}
