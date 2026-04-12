package com.lframework.xingyun.sc.vo.retail.returned;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.xingyun.sc.entity.RetailReturn;
import com.lframework.xingyun.sc.service.retail.RetailReturnService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateRetailReturnVo extends CreateRetailReturnVo {

  private static final long serialVersionUID = 1L;

  /**
   * 退单ID
   */
  @Schema(description = "退单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "退单ID不能为空！")
  private String id;

  @Override
  public void validate() {

    RetailReturnService saleReturnService = ApplicationUtil.getBean(RetailReturnService.class);
    RetailReturn saleReturn = saleReturnService.getById(this.getId());

    this.validate(!StringUtil.isBlank(saleReturn.getOutSheetId()));
  }
}
