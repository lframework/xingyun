package com.lframework.xingyun.template.inner.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.starter.web.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 站内信
 * </p>
 *
 * @author zmj
 */
@Data
@TableName("sys_site_message")
public class SysSiteMessage extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "SysSiteMessage";

  /**
   * ID
   */
  private String id;

  /**
   * 标题
   */
  private String title;

  /**
   * 内容
   */
  private String content;

  /**
   * 接收人ID
   */
  private String receiverId;

  /**
   * 业务键
   */
  private String bizKey;

  /**
   * 是否已读
   */
  private Boolean readed;

  /**
   * 已读时间
   */
  private LocalDateTime readTime;

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
