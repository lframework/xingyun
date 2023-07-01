package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
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
}
