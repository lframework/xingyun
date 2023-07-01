package com.lframework.xingyun.template.gen.dto.simpledb;

import com.lframework.xingyun.template.gen.enums.GenMySqlDataType;
import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class OriSimpleTableColumnDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 表ID
   */
  private String tableId;

  /**
   * 字段名
   */
  private String columnName;

  /**
   * 字段数据类型
   */
  private GenMySqlDataType dataType;

  /**
   * 是否允许为空
   */
  private Boolean isNullable;

  /**
   * 列主键类型
   */
  private Boolean isKey;

  /**
   * 默认值
   */
  private String columnDefault;

  /**
   * 字段排序
   */
  private Integer ordinalPosition;

  /**
   * 字段类型
   */
  private String columnType;

  /**
   * 字段备注
   */
  private String columnComment;

  /**
   * 长度
   */
  private Long len;

  /**
   * 小数位数
   */
  private Integer decimals;
}
