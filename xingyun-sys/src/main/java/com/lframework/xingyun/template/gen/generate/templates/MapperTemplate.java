package com.lframework.xingyun.template.gen.generate.templates;

import java.util.List;
import java.util.Set;
import lombok.Data;

/**
 * 用于生成Mapper.java、Mapper.xml
 */
@Data
public class MapperTemplate {

  /**
   * 包名
   */
  private String packageName;

  /**
   * 类名
   */
  private String className;

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
   * 主键
   */
  private List<Key> keys;

  /**
   * 排序字段
   */
  private List<OrderColumn> orderColumns;

  /**
   * 实体类配置
   */
  private EntityTemplate entity;

  /**
   * 需要import的包
   */
  private Set<String> importPackages;

  /**
   * 查询参数配置
   */
  private QueryParamsTemplate queryParams;

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
     * 数据表列名
     */
    private String columnName;
  }

  @Data
  public static class OrderColumn {

    /**
     * 数据表列名
     */
    private String columnName;

    /**
     * 排序类型
     */
    private String orderType;
  }
}
