package com.lframework.xingyun.template.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.xingyun.template.gen.components.TableColumn;
import com.lframework.xingyun.template.gen.enums.GenDataType;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-05-28
 */
@Data
@TableName("gen_simple_table_column")
public class GenSimpleTableColumn extends BaseEntity implements BaseDto, TableColumn {

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
  private GenDataType dataType;

  /**
   * 是否允许为空
   */
  private Boolean isNullable;

  /**
   * 是否主键
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

  @Override
  public String getDbColumnName() {
    return this.columnName;
  }
}
