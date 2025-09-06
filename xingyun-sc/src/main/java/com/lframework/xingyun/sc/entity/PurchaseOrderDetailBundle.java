package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;

@Data
@TableName("tbl_purchase_order_detail_bundle")
public class PurchaseOrderDetailBundle extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "PurchaseOrderDetailBundle";
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 采购单ID
   */
  private String orderId;

  /**
   * 明细ID
   */
  private String detailId;

  /**
   * 组合商品ID
   */
  private String mainProductId;

  /**
   * 组合商品数量
   */
  private BigDecimal orderNum;

  /**
   * 单品ID
   */
  private String productId;

  /**
   * 单品数量
   */
  private BigDecimal productOrderNum;

  /**
   * 单品原价
   */
  private BigDecimal productOriPrice;

  /**
   * 单品含税价格
   */
  private BigDecimal productTaxPrice;

  /**
   * 单品含税金额
   */
  private BigDecimal productTaxAmount;

  /**
   * 单品税率
   */
  private BigDecimal productTaxRate;

  /**
   * 单品明细ID
   */
  private String productDetailId;
}
