package com.lframework.xingyun.template.gen.generate.templates;


import java.util.List;
import java.util.Set;
import lombok.Data;

/**
 * 实体类Template
 */
@Data
public class EntityTemplate {

  /**
   * 包名
   */
  private String packageName;

  /**
   * 库表名
   */
  private String tableName;

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
   * 列信息
   */
  private List<Column> columns;

  /**
   * 需要import的包
   */
  private Set<String> importPackages;

  @Data
  public static class Column {

    /**
     * 是否主键
     */
    private Boolean isKey;

    /**
     * 是否自增主键
     */
    private Boolean autoIncrKey;

    /**
     * 字段类型
     */
    private String dataType;

    /**
     * 前端字段类型 只有字段是枚举时生效，此值为前端枚举类型
     */
    private String frontType;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 是否默认字段名转换方式（下划线转驼峰）
     */
    private Boolean defaultConvertType;

    /**
     * 数据库字段名称
     */
    private String columnName;

    /**
     * 字段备注
     */
    private String description;

    /**
     * 是否填充字段 isKey == false时生效
     */
    private Boolean fill;

    /**
     * 填充策略
     */
    private String fillStrategy;
  }
}
