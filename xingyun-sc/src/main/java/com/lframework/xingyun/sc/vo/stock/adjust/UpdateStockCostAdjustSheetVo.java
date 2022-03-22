package com.lframework.xingyun.sc.vo.stock.adjust;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateStockCostAdjustSheetVo extends CreateStockCostAdjustSheetVo {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @NotBlank(message = "id不能为空！")
  private String id;
}
