package com.lframework.xingyun.template.gen.generate.templates;

import java.util.List;
import java.util.Set;
import lombok.Data;

/**
 * 详情功能Template
 */
@Data
public class DetailTemplate {

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
   * 是否包含状态Tag
   */
  private Boolean hasAvailableTag = Boolean.FALSE;

  /**
   * 字段
   */
  private List<Column> columns;

  /**
   * 主键
   */
  private List<Key> keys;

  /**
   * 是否存在内置枚举
   */
  private Boolean hasFixEnum;

  /**
   * 详情页Span总数量
   */
  private Integer detailSpan;

  /**
   * 需要import的包
   */
  private Set<String> importPackages;

  @Data
  public static class Key {

    /**
     * 字段类型
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
     * 字段备注
     */
    private String description;
  }

  @Data
  public static class Column {

    /**
     * 是否内置枚举
     */
    private Boolean fixEnum;

    /**
     * 枚举的Code的类型 当fixEnum == true时生效
     */
    private String enumCodeType;

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
     * 字段名称（首字母大写）
     */
    private String nameProperty;

    /**
     * 字段备注
     */
    private String description;

    /**
     * 列宽
     */
    private Integer span;

    /**
     * 是否包含状态Tag
     */
    private Boolean hasAvailableTag = Boolean.FALSE;

    /**
     * 数据字典Code
     */
    private String dataDicCode;
  }
}
