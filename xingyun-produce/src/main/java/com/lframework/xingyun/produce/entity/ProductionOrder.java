package com.lframework.xingyun.produce.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.xingyun.produce.enums.ProductionOrderStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 生产工单
 *
 * @author xingyun
 */
@Data
@TableName("tbl_production_order")
public class ProductionOrder extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "ProductionOrder";
  private static final long serialVersionUID = 1L;

  /** ID */
  private String id;

  /** 工单编号 */
  private String code;

  /** BOM ID */
  private String bomId;

  /** 成品商品ID */
  private String productId;

  /** 成品 SKU ID */
  private String skuId;

  /** 仓库ID（成品入库仓） */
  private String scId;

  /** 计划生产数量 */
  private BigDecimal planNum;

  /** 实际生产数量（完工时填写） */
  private BigDecimal actualNum;

  /** 计划开始日期 */
  private LocalDate planStartDate;

  /** 计划完工日期 */
  private LocalDate planEndDate;

  /** 实际完工日期 */
  private LocalDate actualEndDate;

  /** 来源销售订单ID（可为空，表示手动创建） */
  private String saleOrderId;

  /** 负责人ID */
  private String ownerId;

  /** 状态 */
  private ProductionOrderStatus status;

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
