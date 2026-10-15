package com.lframework.xingyun.crm.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.xingyun.crm.enums.ClueStatus;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 销售线索
 *
 * @author xingyun
 */
@Data
@TableName("crm_clue")
public class Clue extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "Clue";
  private static final long serialVersionUID = 1L;

  /** ID */
  private String id;

  /** 线索编号 */
  private String code;

  /** 联系人姓名 */
  private String contactName;

  /** 联系电话 */
  private String contactPhone;

  /** 公司名称 */
  private String companyName;

  /** 线索来源（0-手动录入 1-电话咨询 2-网络推广 3-展会 4-转介绍 5-其他） */
  private Integer source;

  /** 负责销售员ID */
  private String ownerId;

  /** 状态 */
  private ClueStatus status;

  /** 备注 */
  private String description;

  /** 创建人ID */
  @TableField(fill = FieldFill.INSERT)
  private String createById;

  /** 创建人 */
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /** 创建时间 */
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
