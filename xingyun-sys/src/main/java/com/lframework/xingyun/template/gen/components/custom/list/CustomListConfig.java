package com.lframework.xingyun.template.gen.components.custom.list;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.VoidDto;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class CustomListConfig extends BaseBo<VoidDto> implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "CustomListConfig";

  /**
   * 工具栏
   */
  @ApiModelProperty("工具栏")
  private List<Toolbar> toolbars;

  /**
   * 查询参数
   */
  @ApiModelProperty("查询参数")
  private List<QueryParam> queryParams;

  /**
   * 操作列
   */
  @ApiModelProperty("操作列")
  private List<HandleColumn> handleColumns;

  /**
   * 列表配置
   */
  @ApiModelProperty("列表配置")
  private ListConfig listConfig;


  @Data
  public static class QueryParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表别名
     */
    @ApiModelProperty("表别名")
    private String tableAlias;

    /**
     * 字段名称
     */
    @ApiModelProperty("字段名称")
    private String columnName;

    /**
     * 显示名称
     */
    @ApiModelProperty("显示名称")
    private String name;

    /**
     * 前端显示
     */
    @ApiModelProperty("前端显示")
    private Boolean frontShow;

    /**
     * 查询类型
     */
    @ApiModelProperty("查询类型")
    private Integer queryType;

    /**
     * 表单宽度
     */
    @ApiModelProperty("表单宽度")
    private Integer formWidth;

    /**
     * 显示类型
     */
    @ApiModelProperty("显示类型")
    private Integer viewType;

    /**
     * 数据类型
     */
    @ApiModelProperty("数据类型")
    private Integer dataType;

    /**
     * 是否内置枚举
     */
    @ApiModelProperty("是否内置枚举")
    private Boolean fixEnum;

    /**
     * 前端字段类型 只有字段是枚举时生效，此值为前端枚举类型
     */
    @ApiModelProperty("前端字段类型 只有字段是枚举时生效，此值为前端枚举类型")
    private String frontType;

    /**
     * 是否包含状态Tag
     */
    @ApiModelProperty("是否包含状态Tag")
    private Boolean hasAvailableTag = Boolean.FALSE;

    /**
     * 数据字典Code
     */
    @ApiModelProperty("数据字典Code")
    private String dataDicCode;

    /**
     * 自定义选择器ID
     */
    @ApiModelProperty("自定义选择器ID")
    private String customSelectorId;

    /**
     * 默认值
     */
    @ApiModelProperty("默认值")
    private Object defaultValue;
  }

  @Data
  public static class ListConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 列表类型
     */
    @ApiModelProperty("列表类型")
    private Integer listType;

    /**
     * 表单宽度列表
     */
    @ApiModelProperty("表单宽度列表")
    private Integer labelWidth;

    /**
     * 是否分页
     */
    @ApiModelProperty("是否分页")
    private Boolean hasPage;

    /**
     * 是否树形列表
     */
    @ApiModelProperty("是否树形列表")
    private Boolean treeData;

    /**
     * ID字段
     */
    @ApiModelProperty("ID字段")
    private String idColumn;

    /**
     * 父级ID字段
     */
    @ApiModelProperty("父级ID字段")
    private String treePidColumn;

    /**
     * 树形节点字段
     */
    @ApiModelProperty("树形节点字段")
    private String treeNodeColumn;

    /**
     * 子节点Key值
     */
    @ApiModelProperty("子节点Key值")
    private String treeChildrenKey;

    /**
     * 是否允许导出
     */
    @ApiModelProperty("是否允许导出")
    private Boolean allowExport;

    /**
     * 字段
     */
    @ApiModelProperty("字段")
    private List<FieldConfig> fields;
  }

  @Data
  public static class FieldConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 显示名称
     */
    @ApiModelProperty("显示名称")
    private String name;

    /**
     * 字段名称
     */
    @ApiModelProperty("字段名称")
    private String columnName;

    /**
     * 宽度类型
     */
    @ApiModelProperty("宽度类型")
    private Integer widthType;

    /**
     * 宽度
     */
    @ApiModelProperty("宽度")
    private Integer width;

    /**
     * 是否页面排序
     */
    @ApiModelProperty("是否页面排序")
    private Boolean sortable;

    /**
     * 是否数字类型
     */
    @ApiModelProperty("是否数字类型")
    private Boolean isNumberType = Boolean.FALSE;

    /**
     * 是否包含状态Tag
     */
    @ApiModelProperty("是否包含状态Tag")
    private Boolean hasAvailableTag = Boolean.FALSE;

    /**
     * 是否内置枚举
     */
    @ApiModelProperty("是否内置枚举")
    private Boolean fixEnum;

    /**
     * 前端字段类型 只有字段是枚举时生效，此值为前端枚举类型
     */
    @ApiModelProperty("前端字段类型 只有字段是枚举时生效，此值为前端枚举类型")
    private String frontType;

    /**
     * 数据类型
     */
    @ApiModelProperty("数据类型")
    private Integer dataType;

    /**
     * 格式化脚本
     */
    @ApiModelProperty("格式化脚本")
    private String formatter;
  }

  @Data
  public static class Toolbar implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 显示名称
     */
    @ApiModelProperty("显示名称")
    private String name;

    /**
     * 显示类型
     */
    @ApiModelProperty("显示类型")
    private String viewType;

    /**
     * 按钮类型
     */
    @ApiModelProperty("按钮类型")
    private Integer btnType;

    /**
     * 按钮配置
     */
    @ApiModelProperty("按钮配置")
    private String btnConfig;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 请求参数
     */
    @ApiModelProperty("请求参数")
    private String requestParam;
  }

  @Data
  public static class HandleColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 显示名称
     */
    @ApiModelProperty("显示名称")
    private String name;

    /**
     * 显示类型
     */
    @ApiModelProperty("显示类型")
    private String viewType;

    /**
     * 按钮类型
     */
    @ApiModelProperty("按钮类型")
    private Integer btnType;

    /**
     * 按钮配置
     */
    @ApiModelProperty("按钮配置")
    private String btnConfig;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 宽度
     */
    @ApiModelProperty("宽度")
    private Integer width;

    /**
     * 请求参数
     */
    @ApiModelProperty("请求参数")
    private String requestParam;
  }
}
