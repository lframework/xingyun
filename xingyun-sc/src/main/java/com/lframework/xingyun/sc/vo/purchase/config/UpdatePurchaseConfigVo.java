package com.lframework.xingyun.sc.vo.purchase.config;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePurchaseConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 采购收货单是否关联采购订单
   */
  @NotNull(message = "采购收货单是否关联采购订单不能为空！")
  private Boolean receiveRequirePurchase;

  /**
   * 采购收货单是否多次关联采购订单
   */
  @NotNull(message = "采购收货单是否多次关联采购订单不能为空！")
  private Boolean receiveMultipleRelatePurchase;

  /**
   * 采购退货单是否关联采购收货单
   */
  @NotNull(message = "采购退货单是否关联采购收货单不能为空！")
  private Boolean purchaseReturnRequireReceive;

  /**
   * 采购退货单是否多次关联采购收货单
   */
  @NotNull(message = "采购退货单是否多次关联采购收货单不能为空！")
  private Boolean purchaseReturnMultipleRelateReceive;
}
