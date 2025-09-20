package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.xingyun.sc.enums.PurchaseOrderStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-09-12
 */
@Data
@TableName("tbl_purchase_order")
public class PurchaseOrder extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  protected String id;

  /**
   * 单号
   */
  protected String code;

  /**
   * 仓库ID
   */
  protected String scId;

  /**
   * 供应商ID
   */
  protected String supplierId;

  /**
   * 采购员ID
   */
  protected String purchaserId;

  /**
   * 预计到货日期
   */
  protected LocalDate expectArriveDate;

  /**
   * 采购数量
   */
  protected BigDecimal totalNum;

  /**
   * 赠品数量
   */
  protected BigDecimal totalGiftNum;

  /**
   * 采购金额
   */
  protected BigDecimal totalAmount;

  /**
   * 备注
   */
  protected String description;

  /**
   * 创建人ID 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  protected String createById;

  /**
   * 创建人 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  protected String createBy;

  /**
   * 创建时间 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  protected LocalDateTime createTime;

  /**
   * 修改人 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  protected String updateBy;

  /**
   * 修改人ID 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  protected String updateById;

  /**
   * 修改时间 新增和修改时赋值
   */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  protected LocalDateTime updateTime;

  /**
   * 审核人
   */
  protected String approveBy;

  /**
   * 审核时间
   */
  protected LocalDateTime approveTime;

  /**
   * 状态
   */
  protected PurchaseOrderStatus status;

  /**
   * 拒绝原因
   */
  protected String refuseReason;

  /**
   * 关联的审批流程ID
   */
  private Long flowInstanceId;
}
