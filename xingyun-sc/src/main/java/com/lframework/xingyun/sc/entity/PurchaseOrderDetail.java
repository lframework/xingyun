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
 * @since 2021-09-12
 */
@Data
@TableName("tbl_purchase_order_detail")
public class PurchaseOrderDetail extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  protected String id;

  /**
   * 订单ID
   */
  protected String orderId;

  /**
   * 商品ID
   */
  protected String productId;

  /**
   * 采购数量
   */
  protected BigDecimal orderNum;

  /**
   * 采购价
   */
  protected BigDecimal taxPrice;

  /**
   * 是否赠品
   */
  protected Boolean isGift;

  /**
   * 税率（%）
   */
  protected BigDecimal taxRate;

  /**
   * 备注
   */
  protected String description;

  /**
   * 排序编号
   */
  protected Integer orderNo;

  /**
   * 已收货数量
   */
  protected BigDecimal receiveNum;

  /**
   * 含税总金额
   */
  protected BigDecimal taxAmount;
}
