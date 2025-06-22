package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
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
@TableName("tbl_purchase_config")
public class PurchaseConfig extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "PurchaseConfig";
  private static final long serialVersionUID = 1L;
  /**
   * ID
   */
  private String id;

  /**
   * 采购收货单是否关联采购订单
   */
  private Boolean receiveRequirePurchase;

  /**
   * 采购收货单是否多次关联采购订单
   */
  private Boolean receiveMultipleRelatePurchase;

  /**
   * 采购退货单是否关联采购收货单
   */
  private Boolean purchaseReturnRequireReceive;

  /**
   * 采购退货单是否多次关联采购收货单
   */
  private Boolean purchaseReturnMultipleRelateReceive;

  /**
   * 采购订单是否开启审批流程
   */
  private Boolean purchaseRequireBpm;

  /**
   * 采购订单关联的审批流程ID
   */
  private Long purchaseBpmProcessId;

  /**
   * 采购订单关联的审批流程编号
   */
  private String purchaseBpmProcessCode;
}
