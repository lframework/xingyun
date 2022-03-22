package com.lframework.xingyun.api.bo.purchase.config;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.sc.dto.purchase.config.PurchaseConfigDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetPurchaseConfigBo extends BaseBo<PurchaseConfigDto> {

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

  public GetPurchaseConfigBo() {

  }

  public GetPurchaseConfigBo(PurchaseConfigDto dto) {

    super(dto);
  }
}
