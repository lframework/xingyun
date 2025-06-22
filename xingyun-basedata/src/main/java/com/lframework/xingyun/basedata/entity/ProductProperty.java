package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.xingyun.basedata.enums.ColumnDataType;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.PropertyType;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-07-30
 */
@Data
@TableName("base_data_product_property")
public class ProductProperty extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "ProductProperty";
  private static final long serialVersionUID = 1L;
  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 是否必填
   */
  private Boolean isRequired;

  /**
   * 录入类型
   */
  private ColumnType columnType;

  /**
   * 数据类型
   */
  private ColumnDataType columnDataType;

  /**
   * 属性类别
   */
  private PropertyType propertyType;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 备注
   */
  private String description;

  /**
   * 创建人ID 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createById;

  /**
   * 创建人 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /**
   * 创建时间 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 修改人 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateBy;

  /**
   * 修改人ID 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateById;

  /**
   * 修改时间 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
}
