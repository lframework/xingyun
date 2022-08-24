package com.lframework.xingyun.sc.facade.vo.sale.out;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.sc.facade.SaleOutSheetFeignClient;
import com.lframework.xingyun.sc.facade.entity.SaleOutSheet;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateSaleOutSheetVo extends CreateSaleOutSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 出库单ID
   */
  @ApiModelProperty(value = "出库单ID", required = true)
  @NotBlank(message = "出库单ID不能为空！")
  private String id;

  @Override
  public void validate() {

    SaleOutSheetFeignClient saleOutSheetFeignClient = ApplicationUtil.getBean(
        SaleOutSheetFeignClient.class);
    SaleOutSheet saleOutSheet = saleOutSheetFeignClient.getById(this.getId()).getData();

    this.validate(!StringUtil.isBlank(saleOutSheet.getSaleOrderId()));
  }
}
