package com.lframework.xingyun.sc.vo.purchase.config;

import com.lframework.starter.web.core.vo.BaseVo;
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

  /**
   * 采购订单是否开启审批流程
   */
  @ApiModelProperty(value = "采购订单是否开启审批流程", required = true)
  @NotNull(message = "采购订单是否开启审批流程不能为空！")
  private Boolean purchaseRequireBpm;

  /**
   * 采购订单关联的审批流程ID
   */
  @ApiModelProperty(value = "采购订单关联的审批流程ID")
  private String purchaseBpmProcessId;
}
