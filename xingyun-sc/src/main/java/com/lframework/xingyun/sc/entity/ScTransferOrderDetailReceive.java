package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 仓库调拨单收货明细
 * </p>
 *
 * @author zmj
 * @since 2023-05-24
 */
@Data
@TableName("tbl_sc_transfer_order_detail_receive")
public class ScTransferOrderDetailReceive extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 调拨单ID
   */
  private String orderId;

  /**
   * 明细ID
   */
  private String detailId;

  /**
   * 收货数量
   */
  private BigDecimal receiveNum;

  /**
   * 收货金额
   */
  private BigDecimal receiveAmount;

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
   * 创建时间
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;
}
