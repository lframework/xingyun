package com.lframework.xingyun.template.gen.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.xingyun.template.gen.components.Table;
import com.lframework.xingyun.template.gen.enums.GenConvertType;
import com.lframework.xingyun.template.gen.enums.GenStatus;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 数据实体
 * </p>
 *
 * @author zmj
 * @since 2022-09-17
 */
@Data
@TableName("gen_data_entity")
public class GenDataEntity extends BaseEntity implements BaseDto, Table {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "GenDataEntity";

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
   * 生成状态
   */
  private GenStatus genStatus;

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

  /**
   * 数据表所属的数据库名
   */
  private String tableSchema;

  /**
   * 数据库表名
   */
  private String tableName;

  /**
   * 数据库引擎
   */
  private String engine;

  /**
   * 字符校验编码集
   */
  private String tableCollation;

  /**
   * 备注
   */
  private String tableComment;

  /**
   * 转换方式
   */
  private GenConvertType convertType;

  @Override
  public String getSchema() {
    return this.tableSchema;
  }

  @Override
  public String getComment() {
    return this.tableComment;
  }
}
