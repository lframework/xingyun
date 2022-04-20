package com.dstz.adaptor.org.xingyun.constant;

import com.dstz.org.api.constant.GroupTypeConstant;

/**
 * 组织级别
 */
public enum RelationTypeConstant {
  GROUP_USER("groupUser", "用户与组"), POST("groupRole", "岗位"), USER_ROLE("userRole",
      "用户与角色"), POST_USER("groupUserRole",
      "岗位用户");

  private String key;

  private String label;


  RelationTypeConstant(String key, String label) {

    this.setKey(key);
    this.label = label;
  }

  /**
   * 通过组类型转换成与用户的关系类型
   *
   * @param groupType
   * @return
   */
  public static RelationTypeConstant getUserRelationTypeByGroupType(String groupType) {

    GroupTypeConstant type = GroupTypeConstant.fromStr(groupType);

    switch (type) {

      case ORG:
        return RelationTypeConstant.GROUP_USER;

      case POST:
        return RelationTypeConstant.POST_USER;

      case ROLE:
        return RelationTypeConstant.USER_ROLE;
    }

    return null;
  }

  public String label() {

    return label;
  }

  public String getLabel() {

    return label;
  }

  public void setLabel(String label) {

    this.label = label;
  }

  public String getKey() {

    return key;
  }

  public void setKey(String key) {

    this.key = key;
  }

}
