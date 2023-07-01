package com.lframework.xingyun.template.gen.generate.templates;

import java.util.List;
import java.util.Set;
import lombok.Data;

/**
 * 用于Service.java、ServiceImpl.java生成
 */
@Data
public class ServiceTemplate {

  /**
   * 包名
   */
  private String packageName;

  /**
   * 类名
   */
  private String className;

  /**
   * 类名（首字母小写）
   */
  private String classNameProperty;

  /**
   * 模块名称
   */
  private String moduleName;

  /**
   * 业务名称
   */
  private String bizName;

  /**
   * 类描述
   */
  private String classDescription;

  /**
   * 作者
   */
  private String author;

  /**
   * 是否应用缓存
   */
  private Boolean isCache;

  /**
   * 是否内置删除功能
   */
  private Boolean hasDelete;

  /**
   * 需要import的包
   */
  private Set<String> importPackages;

  /**
   * 主键
   */
  private List<Key> keys;

  /**
   * 查询参数配置
   */
  private QueryParamsTemplate queryParams;

  /**
   * 新增配置
   */
  private CreateTemplate create;

  /**
   * 修改配置
   */
  private UpdateTemplate update;

  @Data
  public static class Key {

    /**
     * 类型
     */
    private String dataType;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段名称（首字母大写）
     */
    private String nameProperty;

    /**
     * 数据表列名
     */
    private String columnName;
  }
}
