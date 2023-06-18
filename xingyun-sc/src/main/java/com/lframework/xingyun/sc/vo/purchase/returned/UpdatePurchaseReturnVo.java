package com.lframework.xingyun.sc.vo.purchase.returned;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.sc.entity.PurchaseReturn;
import com.lframework.xingyun.sc.service.purchase.PurchaseReturnService;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePurchaseReturnVo extends CreatePurchaseReturnVo {

  private static final long serialVersionUID = 1L;

  /**
   * 退货单ID
   */
  @ApiModelProperty(value = "退货单ID", required = true)
  @NotBlank(message = "退货单ID不能为空！")
  private String id;

  @Override
  public void validate() {

    PurchaseReturnService purchaseReturnService = ApplicationUtil.getBean(
        PurchaseReturnService.class);
    PurchaseReturn purchaseReturn = purchaseReturnService.getById(this.getId());

    this.validate(!StringUtil.isBlank(purchaseReturn.getReceiveSheetId()));
  }
}
