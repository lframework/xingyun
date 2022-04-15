package com.lframework.xingyun.sc.vo.purchase.receive;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.sc.dto.purchase.receive.ReceiveSheetDto;
import com.lframework.xingyun.sc.service.purchase.IReceiveSheetService;
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

    IReceiveSheetService receiveSheetService = ApplicationUtil.getBean(IReceiveSheetService.class);
    ReceiveSheetDto receiveSheet = receiveSheetService.getById(this.getId());

    this.validate(!StringUtil.isBlank(receiveSheet.getPurchaseOrderId()));
  }
}
