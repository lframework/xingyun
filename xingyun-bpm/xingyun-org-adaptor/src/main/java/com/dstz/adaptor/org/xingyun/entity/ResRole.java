package com.dstz.adaptor.org.xingyun.entity;

import com.dstz.base.api.model.IDModel;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * <pre>
 * 描述：角色资源分配 实体对象
 * </pre>
 */
public class ResRole implements IDModel {

  /**
   * 主键
   */
  protected String id;

  /**
   * 系统ID
   */
  protected String systemId;

  /**
   * 资源ID
   */
  protected String resId;

  /**
   * 角色ID
   */
  protected String roleId;

  /**
   * 角色别名。
   */
  protected String roleAlias;
  /**
   * 资源url连接。
   */
  protected String url;

  /**
   * 资源别名。
   */
  protected String resAlias;


  public void setId(String id) {
    this.id = id;
  }

  public ResRole(String systemId, String resId, String roleId) {
    super();
    this.systemId = systemId;
    this.resId = resId;
    this.roleId = roleId;
  }

  public ResRole() {
  }

  /**
   * 返回 主键
   *
   * @return
   */
  public String getId() {
    return this.id;
  }

  public void setSystemId(String systemId) {
    this.systemId = systemId;
  }

  /**
   * 返回 系统ID
   *
   * @return
   */
  public String getSystemId() {
    return this.systemId;
  }

  public void setResId(String resId) {
    this.resId = resId;
  }

  /**
   * 返回 资源ID
   *
   * @return
   */
  public String getResId() {
    return this.resId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  /**
   * 返回 角色ID
   *
   * @return
   */
  public String getRoleId() {
    return this.roleId;
  }

  public String getRoleAlias() {
    return roleAlias;
  }

  public void setRoleAlias(String roleAlias) {
    this.roleAlias = roleAlias;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getResAlias() {
    return resAlias;
  }

  public void setResAlias(String resAlias) {
    this.resAlias = resAlias;
  }

  /**
   * @see Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", this.id)
        .append("systemId", this.systemId)
        .append("resId", this.resId)
        .append("roleId", this.roleId)
        .toString();
  }
}