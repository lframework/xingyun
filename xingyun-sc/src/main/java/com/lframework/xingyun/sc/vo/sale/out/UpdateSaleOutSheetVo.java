package com.lframework.xingyun.sc.vo.sale.out;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.sc.entity.SaleOutSheet;
import com.lframework.xingyun.sc.service.sale.SaleOutSheetService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSaleOutSheetVo extends CreateSaleOutSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 出库单ID
   */
  @Schema(description = "出库单ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "出库单ID不能为空！")
  private String id;

  @Override
  public void validate() {

    SaleOutSheetService saleOutSheetService = ApplicationUtil.getBean(SaleOutSheetService.class);
    SaleOutSheet saleOutSheet = saleOutSheetService.getById(this.getId());

    this.validate(!StringUtil.isBlank(saleOutSheet.getSaleOrderId()));
  }
}
