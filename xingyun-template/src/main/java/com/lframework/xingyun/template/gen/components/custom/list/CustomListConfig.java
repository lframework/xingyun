package com.lframework.xingyun.template.gen.components.custom.list;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class CustomListConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "CustomListConfig";

  /**
   * 工具栏
   */
  private List<Toolbar> toolbars;

  /**
   * 查询参数
   */
  private List<QueryParam> queryParams;

  /**
   * 操作列
   */
  private List<HandleColumn> handleColumns;

  /**
   * 列表配置
   */
  private ListConfig listConfig;


  @Data
  public static class QueryParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表别名
     */
    private String tableAlias;

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 显示名称
     */
    private String name;

    /**
     * 前端显示
     */
    private Boolean frontShow;

    /**
     * 查询类型
     */
    private Integer queryType;

    /**
     * 表单宽度
     */
    private Integer formWidth;

    /**
     * 显示类型
     */
    private Integer viewType;

    /**
     * 数据类型
     */
    private Integer dataType;

    /**
     * 是否内置枚举
     */
    private Boolean fixEnum;

    /**
     * 前端字段类型 只有字段是枚举时生效，此值为前端枚举类型
     */
    private String frontType;

    /**
     * 是否包含状态Tag
     */
    private Boolean hasAvailableTag = Boolean.FALSE;

    /**
     * 数据字典Code
     */
    private String dataDicCode;

    /**
     * 自定义选择器ID
     */
    private String customSelectorId;

    /**
     * 默认值
     */
    private Object defaultValue;
  }

  @Data
  public static class ListConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 列表类型
     */
    private Integer listType;

    /**
     * 表单宽度列表
     */
    private Integer labelWidth;

    /**
     * 是否分页
     */
    private Boolean hasPage;

    /**
     * 是否树形列表
     */
    private Boolean treeData;

    /**
     * ID字段
     */
    private String idColumn;

    /**
     * 父级ID字段
     */
    private String treePidColumn;

    /**
     * 树形节点字段
     */
    private String treeNodeColumn;

    /**
     * 子节点Key值
     */
    private String treeChildrenKey;

    /**
     * 是否允许导出
     */
    private Boolean allowExport;

    /**
     * 字段
     */
    private List<FieldConfig> fields;
  }

  @Data
  public static class FieldConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 显示名称
     */
    private String name;

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 宽度类型
     */
    private Integer widthType;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 是否页面排序
     */
    private Boolean sortable;

    /**
     * 是否数字类型
     */
    private Boolean isNumberType = Boolean.FALSE;

    /**
     * 是否包含状态Tag
     */
    private Boolean hasAvailableTag = Boolean.FALSE;

    /**
     * 是否内置枚举
     */
    private Boolean fixEnum;

    /**
     * 前端字段类型 只有字段是枚举时生效，此值为前端枚举类型
     */
    private String frontType;

    /**
     * 数据类型
     */
    private Integer dataType;

    /**
     * 格式化脚本
     */
    private String formatter;
  }

  @Data
  public static class Toolbar implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 显示名称
     */
    private String name;

    /**
     * 显示类型
     */
    private String viewType;

    /**
     * 按钮类型
     */
    private Integer btnType;

    /**
     * 按钮配置
     */
    private String btnConfig;

    /**
     * 图标
     */
    private String icon;

    /**
     * 请求参数
     */
    private String requestParam;
  }

  @Data
  public static class HandleColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 显示名称
     */
    private String name;

    /**
     * 显示类型
     */
    private String viewType;

    /**
     * 按钮类型
     */
    private Integer btnType;

    /**
     * 按钮配置
     */
    private String btnConfig;

    /**
     * 图标
     */
    private String icon;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 请求参数
     */
    private String requestParam;
  }
}
