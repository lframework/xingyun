package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-10-21
 */
@Data
@TableName("tbl_sale_order_detail")
public class SaleOrderDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 订单ID
   */
  private String orderId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 销售数量
   */
  private BigDecimal orderNum;

  /**
   * 原价
   */
  private BigDecimal oriPrice;

  /**
   * 现价
   */
  private BigDecimal taxPrice;

  /**
   * 折扣率（%）
   */
  private BigDecimal discountRate;

  /**
   * 是否赠品
   */
  private Boolean isGift;

  /**
   * 税率（%）
   */
  private BigDecimal taxRate;

  /**
   * 备注
   */
  private String description;

  /**
   * 排序编号
   */
  private Integer orderNo;

  /**
   * 已出库数量
   */
  private BigDecimal outNum;

  /**
   * 组合商品原始明细ID
   */
  private String oriBundleDetailId;

  /**
   * 总金额
   */
  private BigDecimal taxAmount;
}
