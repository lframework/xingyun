package com.lframework.xingyun.api.bo.retail.config;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.sc.dto.retail.config.RetailConfigDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetRetailConfigBo extends BaseBo<RetailConfigDto> {

  /**
   * 零售退货单是否关联零售出库单
   */
  private Boolean retailReturnRequireOutStock;

  /**
   * 零售退货单是否多次关联零售出库单
   */
  private Boolean retailReturnMultipleRelateOutStock;

  public GetRetailConfigBo() {

  }

  public GetRetailConfigBo(RetailConfigDto dto) {

    super(dto);
  }
}
