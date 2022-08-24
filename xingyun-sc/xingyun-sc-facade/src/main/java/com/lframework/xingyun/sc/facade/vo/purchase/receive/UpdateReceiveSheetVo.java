package com.lframework.xingyun.sc.facade.vo.purchase.receive;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.sc.facade.ReceiveSheetFeignClient;
import com.lframework.xingyun.sc.facade.entity.ReceiveSheet;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
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

    ReceiveSheetFeignClient receiveSheetFeignClient = ApplicationUtil.getBean(
        ReceiveSheetFeignClient.class);
    ReceiveSheet receiveSheet = receiveSheetFeignClient.getById(this.getId()).getData();

    this.validate(!StringUtil.isBlank(receiveSheet.getPurchaseOrderId()));
  }
}
