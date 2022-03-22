package com.lframework.xingyun.sc.vo.retail.returned;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.sc.dto.retail.returned.RetailReturnDto;
import com.lframework.xingyun.sc.service.retail.IRetailReturnService;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateRetailReturnVo extends CreateRetailReturnVo {

  private static final long serialVersionUID = 1L;

  /**
   * 退单ID
   */
  @NotBlank(message = "退单ID不能为空！")
  private String id;

  @Override
  public void validate() {

    IRetailReturnService saleReturnService = ApplicationUtil.getBean(IRetailReturnService.class);
    RetailReturnDto saleReturn = saleReturnService.getById(this.getId());

    this.validate(!StringUtil.isBlank(saleReturn.getOutSheetId()));
  }
}
