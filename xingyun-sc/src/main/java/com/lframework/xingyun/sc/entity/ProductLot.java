package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tbl_product_lot")
public class ProductLot extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 批次号
   */
  private String lotCode;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 供应商ID
   */
  private String supplierId;

  /**
   * 税率（%）
   */
  private BigDecimal taxRate;

  /**
   * 创建时间
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 业务单据ID
   */
  private String bizId;

  /**
   * 业务单据明细ID
   */
  private String bizDetailId;

  /**
   * 业务单据号
   */
  private String bizCode;

  /**
   * 业务类型
   */
  private ProductStockBizType bizType;
}
