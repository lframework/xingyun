package com.dstz.adaptor.org.xingyun.entity;

import com.dstz.base.core.model.BaseModel;
import com.dstz.org.api.constant.GroupTypeConstant;
import com.dstz.org.api.model.IGroup;
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * <pre>
 * 描述：角色管理 实体对象
 * </pre>
 */
public class Role extends BaseModel implements IGroup {

  /**
   * 角色名称
   */
  protected String name;

  /**
   * 角色别名
   */
  protected String code;

  /**
   * 0：禁用，1：启用
   */
  protected Integer available = 1;

  /**
   * 角色描述
   */
  protected String description = "";

  /**
   * 返回 角色名称
   *
   * @return
   */
  public String getName() {

    return this.name;
  }

  public void setName(String name) {

    this.name = name;
  }

  public String getCode() {

    return code;
  }

  public void setCode(String code) {

    this.code = code;
  }

  public Integer getAvailable() {

    return available;
  }

  public void setAvailable(Integer available) {

    this.available = available;
  }

  /**
   * @see Object#toString()
   */
  public String toString() {

    return new ToStringBuilder(this).append("id", this.id).append("name", this.name)
        .append("code", this.code)
        .append("available", this.available).toString();
  }

  public String getGroupId() {

    return this.id;
  }

  public String getGroupCode() {

    return this.code;
  }

  public Long getSn() {

    return Long.valueOf(1);
  }

  public String getGroupType() {

    return GroupTypeConstant.ROLE.key();
  }

  public String getParentId() {

    return "";
  }

  public String getPath() {

    return this.name;
  }

  public Map<String, Object> getParams() {

    return null;
  }

  public String getDescription() {

    return description;
  }

  public void setDescription(String description) {

    this.description = description;
  }

  @Override
  public String getGroupName() {

    return this.name;
  }
}