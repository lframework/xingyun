package com.lframework.xingyun.template.gen.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 数据对象
 * </p>
 *
 * @author zmj
 * @since 2022-09-24
 */
@Data
@TableName("gen_data_obj")
public class GenDataObj extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "GenDataObj";

  /**
   * ID
   */
  private String id;

  /**
   * 名称
   */
  private String name;

  /**
   * 分类ID
   */
  private String categoryId;

  /**
   * 主表ID
   */
  private String mainTableId;

  /**
   * 主表别名
   */
  private String mainTableAlias;

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
