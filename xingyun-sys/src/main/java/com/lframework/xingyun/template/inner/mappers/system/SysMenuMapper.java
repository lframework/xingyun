package com.lframework.xingyun.template.inner.mappers.system;

import com.lframework.xingyun.template.inner.dto.MenuDto;
import com.lframework.xingyun.template.inner.entity.SysMenu;
import com.lframework.starter.web.mapper.BaseMapper;
import com.lframework.xingyun.template.inner.vo.system.menu.SysMenuSelectorVo;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统菜单 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-05-10
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

  /**
   * 系统菜单列表
   *
   * @return
   */
  List<SysMenu> query(@Param("moduleIds") List<Integer> moduleIds);

  /**
   * 根据角色ID查询已授权的菜单
   *
   * @param roleId
   * @return
   */
  List<SysMenu> getByRoleId(@Param("roleId") String roleId,
      @Param("moduleIds") List<Integer> moduleIds);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysMenu findById(String id);

  /**
   * 系统菜单选择器数据
   *
   * @return
   */
  List<SysMenu> selector(@Param("vo") SysMenuSelectorVo vo, @Param("moduleIds") List<Integer> moduleIds);

  /**
   * 根据ID查询子节点
   *
   * @param id
   * @return
   */
  List<SysMenu> getChildrenById(String id);

  /**
   * 根据用户ID查询菜单
   *
   * @param userId
   * @param isAdmin 是否为管理员
   * @return
   */
  List<MenuDto> getMenuByUserId(@Param("userId") String userId, @Param("isAdmin") boolean isAdmin,
      @Param("moduleIds") List<Integer> moduleIds);

  /**
   * 根据用户ID查询收藏的菜单
   *
   * @param userId
   * @return
   */
  List<String> getCollectMenuIds(@Param("userId") String userId);

  /**
   * 根据用户ID查询权限
   *
   * @param userId
   * @return
   */
  Set<String> getPermissionsByUserId(@Param("userId") String userId,
      @Param("isAdmin") boolean isAdmin,
      @Param("moduleIds") List<Integer> moduleIds);

  /**
   * 根据用户ID查询角色权限
   *
   * @param userId
   * @return
   */
  Set<String> getRolePermissionByUserId(@Param("userId") String userId);

  /**
   * 收藏菜单
   *
   * @param id
   * @param userId
   * @param menuId
   */
  void collectMenu(@Param("id") String id, @Param("userId") String userId,
      @Param("menuId") String menuId);

  /**
   * 取消收藏菜单
   *
   * @param userId
   * @param menuId
   */
  void cancelCollectMenu(@Param("userId") String userId, @Param("menuId") String menuId);
}
