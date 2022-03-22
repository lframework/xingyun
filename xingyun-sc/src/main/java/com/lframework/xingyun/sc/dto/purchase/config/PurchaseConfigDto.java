package com.lframework.xingyun.sc.dto.purchase.config;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class PurchaseConfigDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "PurchaseConfigDto";

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
