package com.dstz.adaptor.org.xingyun.dao;

import com.dstz.adaptor.org.xingyun.dto.DeptDTO;
import com.dstz.adaptor.org.xingyun.dto.PositionDTO;
import com.dstz.adaptor.org.xingyun.dto.RoleDTO;
import java.util.List;

public interface GroupDao {

  /**
   * 根据用户ID查询部门
   *
   * @param userId
   * @return
   */
  List<DeptDTO> getDeptByUserId(String userId);

  /**
   * 根据用户ID查询角色
   *
   * @param userId
   * @return
   */
  List<RoleDTO> getRoleByUserId(String userId);

  /**
   * 根据用户ID查询岗位
   *
   * @param userId
   * @return
   */
  List<PositionDTO> getPositionByUserId(String userId);

  /**
   * 根据ID查询部门
   *
   * @param id
   * @return
   */
  DeptDTO getDeptById(String id);

  /**
   * 根据ID查询角色
   *
   * @param id
   * @return
   */
  RoleDTO getRoleById(String id);

  /**
   * 根据ID查询岗位
   *
   * @param id
   * @return
   */
  PositionDTO getPositionById(String id);

  /**
   * 根据编号查询部门
   *
   * @param code
   * @return
   */
  DeptDTO getDeptByCode(String code);

  /**
   * 根据编号查询角色
   *
   * @param code
   * @return
   */
  RoleDTO getRoleByCode(String code);

  /**
   * 根据编号查询岗位
   *
   * @param code
   * @return
   */
  PositionDTO getPositionByCode(String code);
}
