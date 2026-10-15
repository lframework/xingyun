package com.lframework.xingyun.crm.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.xingyun.crm.enums.OpportunityStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 销售商机
 *
 * @author xingyun
 */
@Data
@TableName("crm_opportunity")
public class Opportunity extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "Opportunity";
  private static final long serialVersionUID = 1L;

  /** ID */
  private String id;

  /** 商机编号 */
  private String code;

  /** 商机名称 */
  private String name;

  /** 来源线索ID（可为空，表示直接创建） */
  private String clueId;

  /** 关联客户ID（basedata Customer） */
  private String customerId;

  /** 负责销售员ID */
  private String ownerId;

  /** 预计金额 */
  private BigDecimal estimatedAmount;

  /** 预计成交日期 */
  private LocalDate expectedCloseDate;

  /** 阶段（0-初步接触 1-需求确认 2-方案报价 3-商务谈判 4-赢单 5-输单） */
  private Integer stage;

  /** 状态 */
  private OpportunityStatus status;

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
