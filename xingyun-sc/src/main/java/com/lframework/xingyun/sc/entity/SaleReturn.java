package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.xingyun.sc.enums.SaleReturnStatus;
import com.lframework.xingyun.sc.enums.SettleStatus;
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
 * @since 2021-11-04
 */
@Data
@TableName("tbl_sale_return")
public class SaleReturn extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 单号
   */
  private String code;

  /**
   * 仓库ID
   */
  private String scId;

  /**
   * 客户ID
   */
  private String customerId;

  /**
   * 销售员ID
   */
  private String salerId;

  /**
   * 付款日期
   */
  private LocalDate paymentDate;

  /**
   * 出库单ID
   */
  private String outSheetId;

  /**
   * 商品数量
   */
  private BigDecimal totalNum;

  /**
   * 赠品数量
   */
  private BigDecimal totalGiftNum;

  /**
   * 退货金额
   */
  private BigDecimal totalAmount;

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
   * 审核人
   */
  private String approveBy;

  /**
   * 审核时间
   */
  private LocalDateTime approveTime;

  /**
   * 状态
   */
  private SaleReturnStatus status;

  /**
   * 拒绝原因
   */
  private String refuseReason;

  /**
   * 结算状态
   */
  private SettleStatus settleStatus;


}
