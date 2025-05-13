package com.lframework.xingyun.template.inner.service.system;

import com.lframework.starter.web.resp.PageResult;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.entity.SysRoleCategory;
import com.lframework.xingyun.template.inner.vo.system.role.category.CreateSysRoleCategoryVo;
import com.lframework.xingyun.template.inner.vo.system.role.category.SysRoleCategorySelectorVo;
import com.lframework.xingyun.template.inner.vo.system.role.category.UpdateSysRoleCategoryVo;
import java.util.List;

/**
 * 角色分类 Service
 *
 * @author zmj
 */
public interface SysRoleCategoryService extends BaseMpService<SysRoleCategory> {

  /**
   * 查询列表
   *
   * @return
   */
  List<SysRoleCategory> queryList();

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  PageResult<SysRoleCategory> selector(Integer pageIndex, Integer pageSize,
      SysRoleCategorySelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysRoleCategory findById(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysRoleCategoryVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysRoleCategoryVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
