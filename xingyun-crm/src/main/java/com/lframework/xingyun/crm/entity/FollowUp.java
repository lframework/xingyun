package com.lframework.xingyun.crm.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 跟进记录（可挂线索或商机）
 *
 * @author xingyun
 */
@Data
@TableName("crm_follow_up")
public class FollowUp extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "FollowUp";
  private static final long serialVersionUID = 1L;

  /** ID */
  private String id;

  /** 关联线索ID（与opportunityId二选一） */
  private String clueId;

  /** 关联商机ID（与clueId二选一） */
  private String opportunityId;

  /** 跟进方式（0-电话 1-邮件 2-上门拜访 3-微信/企业微信 4-其他） */
  private Integer followType;

  /** 跟进内容 */
  private String content;

  /** 下次跟进日期 */
  private LocalDate nextFollowDate;

  /** 跟进人ID */
  @TableField(fill = FieldFill.INSERT)
  private String createById;

  /** 跟进人 */
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /** 跟进时间 */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /** 修改人 */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateBy;

  /** 修改人ID */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateById;

  /** 修改时间 */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
}
