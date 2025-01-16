package com.lframework.xingyun.template.gen.dto.data.entity;

import com.lframework.xingyun.template.gen.enums.GenDataType;
import com.lframework.xingyun.template.gen.enums.GenOrderType;
import com.lframework.xingyun.template.gen.enums.GenViewType;
import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class GenDataEntityDetailDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

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
  private Boolean fixEnum = Boolean.FALSE;

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
  private Boolean isOrder = Boolean.FALSE;

  /**
   * 排序类型
   */
  private GenOrderType orderType;
}
