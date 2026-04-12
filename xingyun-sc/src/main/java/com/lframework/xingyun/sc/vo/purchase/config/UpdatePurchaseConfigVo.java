package com.lframework.xingyun.sc.vo.purchase.config;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePurchaseConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 采购收货单是否关联采购订单
   */
  @Schema(description = "采购收货单是否关联采购订单", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "采购收货单是否关联采购订单不能为空！")
  private Boolean receiveRequirePurchase;

  /**
   * 采购收货单是否多次关联采购订单
   */
  @Schema(description = "采购收货单是否多次关联采购订单", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "采购收货单是否多次关联采购订单不能为空！")
  private Boolean receiveMultipleRelatePurchase;

  /**
   * 采购退货单是否关联采购收货单
   */
  @Schema(description = "采购退货单是否关联采购收货单", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "采购退货单是否关联采购收货单不能为空！")
  private Boolean purchaseReturnRequireReceive;

  /**
   * 采购退货单是否多次关联采购收货单
   */
  @Schema(description = "采购退货单是否多次关联采购收货单", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "采购退货单是否多次关联采购收货单不能为空！")
  private Boolean purchaseReturnMultipleRelateReceive;

  /**
   * 采购订单是否开启审批流程
   */
  @Schema(description = "采购订单是否开启审批流程", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "采购订单是否开启审批流程不能为空！")
  private Boolean purchaseRequireBpm;

  /**
   * 采购订单关联的审批流程ID
   */
  @Schema(description = "采购订单关联的审批流程ID")
  private String purchaseBpmProcessId;
}
