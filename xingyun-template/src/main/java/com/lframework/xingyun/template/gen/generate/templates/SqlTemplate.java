package com.lframework.xingyun.template.gen.generate.templates;

import lombok.Data;

/**
 * 用于生成Sql
 */
@Data
public class SqlTemplate {

  /**
   * 模块名称
   */
  private String moduleName;

  /**
   * 业务名称
   */
  private String bizName;

  /**
   * 类名
   */
  private String className;

  /**
   * 类描述
   */
  private String classDescription;

  /**
   * 父级菜单ID
   */
  private String parentMenuId;

  /**
   * 菜单ID
   */
  private String menuId;

  /**
   * 本级菜单编号
   */
  private String menuCode;

  /**
   * 本级菜单名称
   */
  private String menuName;

  /**
   * 新增配置
   */
  private CreateTemplate create;

  /**
   * 修改配置
   */
  private UpdateTemplate update;
}
