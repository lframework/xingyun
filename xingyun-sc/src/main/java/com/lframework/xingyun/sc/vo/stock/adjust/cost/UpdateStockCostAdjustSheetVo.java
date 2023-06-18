package com.lframework.xingyun.sc.vo.stock.adjust.cost;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateStockCostAdjustSheetVo extends CreateStockCostAdjustSheetVo {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "id不能为空！")
  private String id;
}
