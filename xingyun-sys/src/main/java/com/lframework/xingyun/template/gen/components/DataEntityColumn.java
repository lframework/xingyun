package com.lframework.xingyun.template.gen.components;

import com.lframework.xingyun.template.gen.enums.GenDataType;
import com.lframework.xingyun.template.gen.enums.GenOrderType;
import com.lframework.xingyun.template.gen.enums.GenViewType;
import lombok.Data;

@Data
public class DataEntityColumn {

  /**
   * ID
   */
  private String id;

  /**
   * 字段显示名称
   */
  private String name;

  /**
   * 字段名称
   */
  private String columnName;

  /**
   * 是否主键
   */
  private Boolean isKey;

  /**
   * 数据类型
   */
  private GenDataType dataType;

  /**
   * 排序编号
   */
  private Integer columnOrder;

  /**
   * 备注
   */
  private String description;

  /**
   * 显示类型
   */
  private GenViewType viewType;

  /**
   * 是否内置枚举
   */
  private Boolean fixEnum;

  /**
   * 后端枚举名
   */
  private String enumBack;

  /**
   * 前端枚举名
   */
  private String enumFront;

  /**
   * 正则表达式
   */
  private String regularExpression;

  /**
   * 是否排序字段
   */
  private Boolean isOrder;

  /**
   * 排序类型
   */
  private GenOrderType orderType;

  /**
   * 数据表字段信息
   */
  private TableColumn tableColumn;

  /**
   * 新增配置
   */
  private CreateColumnConfig createConfig;

  /**
   * 修改配置
   */
  private UpdateColumnConfig updateConfig;

  /**
   * 查询配置
   */
  private QueryColumnConfig queryConfig;

  /**
   * 查询参数配置
   */
  private QueryParamsColumnConfig queryParamsConfig;

  /**
   * 详情配置
   */
  private DetailColumnConfig detailConfig;

  /**
   * 数据字典ID
   */
  private String dataDicId;

  /**
   * 自定义选择器ID
   */
  private String customSelectorId;

  /**
   * 长度
   */
  private Long len;

  /**
   * 小数点位数
   */
  private Integer decimals;
}
