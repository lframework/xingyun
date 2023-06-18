package com.lframework.xingyun.sc.vo.purchase.receive;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.xingyun.sc.entity.ReceiveSheet;
import com.lframework.xingyun.sc.service.purchase.ReceiveSheetService;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateReceiveSheetVo extends CreateReceiveSheetVo {

  private static final long serialVersionUID = 1L;

  /**
   * 收货单ID
   */
  @ApiModelProperty(value = "收货单ID", required = true)
  @NotBlank(message = "收货单ID不能为空！")
  private String id;

  @Override
  public void validate() {

    ReceiveSheetService receiveSheetService = ApplicationUtil.getBean(ReceiveSheetService.class);
    ReceiveSheet receiveSheet = receiveSheetService.getById(this.getId());

    this.validate(!StringUtil.isBlank(receiveSheet.getPurchaseOrderId()));
  }
}
