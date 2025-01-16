package com.lframework.xingyun.sc.vo.sale.returned;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.sc.entity.SaleReturn;
import com.lframework.xingyun.sc.service.sale.SaleReturnService;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSaleReturnVo extends CreateSaleReturnVo {

  private static final long serialVersionUID = 1L;

  /**
   * 退单ID
   */
  @ApiModelProperty(value = "退单ID", required = true)
  @NotBlank(message = "退单ID不能为空！")
  private String id;

  @Override
  public void validate() {

    SaleReturnService saleReturnService = ApplicationUtil.getBean(SaleReturnService.class);
    SaleReturn saleReturn = saleReturnService.getById(this.getId());

    this.validate(!StringUtil.isBlank(saleReturn.getOutSheetId()));
  }
}
