package com.lframework.xingyun.template.inner.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 租户
 * </p>
 *
 * @author zmj
 * @since 2023-03-05
 */
@Data
@TableName("tenant")
public class Tenant extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "Tenant";

  /**
   * ID
   */
  @TableId(type = IdType.AUTO)
  private Integer id;

  /**
   * 名称
   */
  private String name;

  /**
   * JdbcUrl
   */
  private String jdbcUrl;

  /**
   * Jdbc用户名
   */
  private String jdbcUsername;

  /**
   * Jdbc密码
   */
  private String jdbcPassword;

  /**
   * 状态
   */
  private Boolean available;

  /**
   * 创建时间 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 修改时间 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
}
