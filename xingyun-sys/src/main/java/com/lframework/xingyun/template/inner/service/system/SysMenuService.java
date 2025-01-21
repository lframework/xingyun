package com.lframework.xingyun.template.inner.service.system;

import com.lframework.xingyun.template.inner.dto.MenuDto;
import com.lframework.xingyun.template.inner.entity.SysMenu;
import com.lframework.starter.web.service.BaseMpService;
import com.lframework.xingyun.template.inner.vo.system.menu.CreateSysMenuVo;
import com.lframework.xingyun.template.inner.vo.system.menu.SysMenuSelectorVo;
import com.lframework.xingyun.template.inner.vo.system.menu.UpdateSysMenuVo;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author zmj
 * @since 2021-05-10
 */
public interface SysMenuService extends BaseMpService<SysMenu> {

  /**
   * 系统菜单列表
   *
   * @return
   */
  List<SysMenu> queryList(List<Integer> moduleIds);

  /**
   * 根据角色ID查询已授权的菜单
   *
   * @param roleId
   * @return
   */
  List<SysMenu> getByRoleId(String roleId, List<Integer> moduleIds);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysMenu findById(String id);

  /**
   * 创建系统菜单
   *
   * @param vo
   */
  String create(CreateSysMenuVo vo);

  /**
   * 修改系统菜单
   *
   * @param vo
   */
  void update(UpdateSysMenuVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);

  /**
   * 系统菜单选择器数据
   *
   * @return
   */
  List<SysMenu> selector(SysMenuSelectorVo vo, List<Integer> moduleIds);

  /**
   * 批量启用
   *
   * @param id
   */
  void enable(String id);

  /**
   * 停用
   *
   * @param id
   */
  void unable(String id);

  /**
   * 是否存在权限
   *
   * @param permission
   * @return
   */
  Boolean existPermission(String permission);

  /**
   * 根据用户ID查询菜单
   *
   * @param userId
   * @param isAdmin 是否为管理员
   * @return
   */
  List<MenuDto> getMenuByUserId(String userId, boolean isAdmin, List<Integer> moduleIds);

  /**
   * 根据用户ID查询权限
   *
   * @param userId
   * @return
   */
  Set<String> getPermissionsByUserId(String userId, boolean isAdmin, List<Integer> moduleIds);

  /**
   * 根据用户ID查询角色权限
   * @param userId
   * @return
   */
  Set<String> getRolePermissionByUserId(String userId);

  /**
   * 收藏菜单
   *
   * @param userId
   * @param menuId
   */
  void collect(String userId, String menuId);

  /**
   * 取消收藏菜单
   *
   * @param userId
   * @param menuId
   */
  void cancelCollect(String userId, String menuId);
}
