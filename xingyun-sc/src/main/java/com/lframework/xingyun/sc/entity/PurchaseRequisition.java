package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.xingyun.sc.enums.PurchaseRequisitionStatus;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 采购建议单（由生产工单 BOM 展开自动生成）
 */
@Data
@TableName("tbl_purchase_requisition")
public class PurchaseRequisition extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "PurchaseRequisition";
  private static final long serialVersionUID = 1L;

  /** ID */
  private String id;

  /** 单号 */
  private String code;

  /** 来源生产工单ID */
  private String productionOrderId;

  /** 仓库ID（目标收货仓） */
  private String scId;

  /** 状态 */
  private PurchaseRequisitionStatus status;

  /** 备注 */
  private String description;

  @TableField(fill = FieldFill.INSERT)
  private String createById;

  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateBy;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateById;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
}
