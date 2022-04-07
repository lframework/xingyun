package com.dstz.adaptor.org.xingyun.dto;

import com.dstz.org.api.model.IUserRole;

public class UserRoleDTO implements IUserRole {

  /**
   * 角色标识
   */
  private String alias;

  /**
   * 用户名
   */
  private String fullname;

  /**
   * 角色名
   */
  private String roleName;

  /**
   * 角色ID
   */
  private String roleId;

  /**
   * 用户ID
   */
  private String userId;

  /**
   * 用户账户
   */
  private String account = "";

  @Override
  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  @Override
  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  @Override
  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  @Override
  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  @Override
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Override
  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }
}
