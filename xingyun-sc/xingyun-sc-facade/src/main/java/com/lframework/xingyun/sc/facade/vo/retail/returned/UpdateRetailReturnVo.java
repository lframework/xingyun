package com.lframework.xingyun.sc.facade.vo.retail.returned;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.sc.facade.RetailReturnFeignClient;
import com.lframework.xingyun.sc.facade.entity.RetailReturn;
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

    RetailReturnFeignClient retailReturnFeignClient = ApplicationUtil.getBean(
        RetailReturnFeignClient.class);
    RetailReturn saleReturn = retailReturnFeignClient.getById(this.getId()).getData();

    this.validate(!StringUtil.isBlank(saleReturn.getOutSheetId()));
  }
}
