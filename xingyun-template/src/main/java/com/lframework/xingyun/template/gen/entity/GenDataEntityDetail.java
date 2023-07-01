package com.lframework.xingyun.template.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.xingyun.template.gen.components.TableColumn;
import com.lframework.xingyun.template.gen.enums.GenDataType;
import com.lframework.xingyun.template.gen.enums.GenOrderType;
import com.lframework.xingyun.template.gen.enums.GenViewType;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

/**
 * <p>
 * 数据实体明细
 * </p>
 *
 * @author zmj
 * @since 2022-09-17
 */
@Data
@TableName("gen_data_entity_detail")
public class GenDataEntityDetail extends BaseEntity implements BaseDto, TableColumn {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 数据对象ID
   */
  private String entityId;

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
   * 小数位数
   */
  private Integer decimals;

  /**
   * 字段名称
   */
  private String dbColumnName;

  /**
   * 字段数据类型
   */
  private GenDataType dbDataType;

  /**
   * 是否允许为空
   */
  private Boolean isNullable;

  /**
   * 默认值
   */
  private String columnDefault;

  /**
   * 字段排序
   */
  private Integer ordinalPosition;

  /**
   * 字段备注
   */
  private String columnComment;

  /**
   * 长度
   */
  private Long dbLen;

  /**
   * 小数位数
   */
  private Integer dbDecimals;
}
