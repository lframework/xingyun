package com.lframework.xingyun.sc.facade.vo.purchase.config;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePurchaseConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 采购收货单是否关联采购订单
   */
  @ApiModelProperty(value = "采购收货单是否关联采购订单", required = true)
  @NotNull(message = "采购收货单是否关联采购订单不能为空！")
  private Boolean receiveRequirePurchase;

  /**
   * 采购收货单是否多次关联采购订单
   */
  @ApiModelProperty(value = "采购收货单是否多次关联采购订单", required = true)
  @NotNull(message = "采购收货单是否多次关联采购订单不能为空！")
  private Boolean receiveMultipleRelatePurchase;

  /**
   * 采购退货单是否关联采购收货单
   */
  @ApiModelProperty(value = "采购退货单是否关联采购收货单", required = true)
  @NotNull(message = "采购退货单是否关联采购收货单不能为空！")
  private Boolean purchaseReturnRequireReceive;

  /**
   * 采购退货单是否多次关联采购收货单
   */
  @ApiModelProperty(value = "采购退货单是否多次关联采购收货单", required = true)
  @NotNull(message = "采购退货单是否多次关联采购收货单不能为空！")
  private Boolean purchaseReturnMultipleRelateReceive;
}
