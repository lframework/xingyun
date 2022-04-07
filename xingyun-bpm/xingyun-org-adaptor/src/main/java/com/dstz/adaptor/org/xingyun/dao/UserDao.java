package com.dstz.adaptor.org.xingyun.dao;

import com.dstz.adaptor.org.xingyun.dto.UserDTO;
import com.dstz.adaptor.org.xingyun.dto.UserRoleDTO;
import java.util.List;

public interface UserDao {

  /**
   * 根据用户ID获取用户的对象。
   *
   * @param userId 用户ID
   * @return
   */
  UserDTO getUserById(String userId);

  /**
   * 根据用户帐号获取用户对象。
   *
   * @param account
   * @return
   */
  UserDTO getUserByAccount(String account);

  /**
   * 获取用户的角色关系
   *
   * @param userId
   * @return
   */
  List<UserRoleDTO> getUserRole(String userId);

  /**
   * 根据岗位ID获取用户对象。
   *
   * @param positionId
   * @return
   */
  List<UserDTO> getUserByPositionId(String positionId);

  /**
   * 根据岗位ID获取用户对象。
   *
   * @param positionId
   * @return
   */
  List<UserDTO> getUserByPositionIds(String[] positionId);

  /**
   * 根据角色ID获取用户对象。
   *
   * @param roleId
   * @return
   */
  List<UserDTO> getUserByRoleId(String roleId);
}
