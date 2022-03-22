package com.lframework.xingyun.sc.vo.purchase.returned;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.sc.dto.purchase.returned.PurchaseReturnDto;
import com.lframework.xingyun.sc.service.purchase.IPurchaseReturnService;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePurchaseReturnVo extends CreatePurchaseReturnVo {

  private static final long serialVersionUID = 1L;

  /**
   * 退货单ID
   */
  @NotBlank(message = "退货单ID不能为空！")
  private String id;

  @Override
  public void validate() {

    IPurchaseReturnService purchaseReturnService = ApplicationUtil
        .getBean(IPurchaseReturnService.class);
    PurchaseReturnDto purchaseReturn = purchaseReturnService.getById(this.getId());

    this.validate(!StringUtil.isBlank(purchaseReturn.getReceiveSheetId()));
  }
}
