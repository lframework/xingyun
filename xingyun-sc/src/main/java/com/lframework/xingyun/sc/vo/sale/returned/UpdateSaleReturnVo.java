package com.lframework.xingyun.sc.vo.sale.returned;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.sc.entity.SaleReturn;
import com.lframework.xingyun.sc.service.sale.SaleReturnService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSaleReturnVo extends CreateSaleReturnVo {

  private static final long serialVersionUID = 1L;

  /**
   * 退单ID
   */
  @Schema(description = "退单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "退单ID不能为空！")
  private String id;

  @Override
  public void validate() {

    SaleReturnService saleReturnService = ApplicationUtil.getBean(SaleReturnService.class);
    SaleReturn saleReturn = saleReturnService.getById(this.getId());

    this.validate(!StringUtil.isBlank(saleReturn.getOutSheetId()));
  }
}
