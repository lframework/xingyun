package com.lframework.xingyun.sc.vo.retail.returned;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.sc.entity.RetailReturn;
import com.lframework.xingyun.sc.service.retail.IRetailReturnService;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateRetailReturnVo extends CreateRetailReturnVo {

  private static final long serialVersionUID = 1L;

  /**
   * 退单ID
   */
  @ApiModelProperty(value = "退单ID", required = true)
  @NotBlank(message = "退单ID不能为空！")
  private String id;

  @Override
  public void validate() {

    IRetailReturnService saleReturnService = ApplicationUtil.getBean(IRetailReturnService.class);
    RetailReturn saleReturn = saleReturnService.getById(this.getId());

    this.validate(!StringUtil.isBlank(saleReturn.getOutSheetId()));
  }
}
