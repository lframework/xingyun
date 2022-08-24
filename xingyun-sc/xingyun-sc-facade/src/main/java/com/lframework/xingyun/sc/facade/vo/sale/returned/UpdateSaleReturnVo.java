package com.lframework.xingyun.sc.facade.vo.sale.returned;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.sc.facade.SaleReturnFeignClient;
import com.lframework.xingyun.sc.facade.entity.SaleReturn;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
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

    SaleReturnFeignClient saleReturnFeignClient = ApplicationUtil.getBean(
        SaleReturnFeignClient.class);
    SaleReturn saleReturn = saleReturnFeignClient.getById(this.getId()).getData();

    this.validate(!StringUtil.isBlank(saleReturn.getOutSheetId()));
  }
}
