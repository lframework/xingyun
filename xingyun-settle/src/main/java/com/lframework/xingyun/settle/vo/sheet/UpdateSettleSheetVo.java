package com.lframework.xingyun.settle.vo.sheet;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSettleSheetVo extends CreateSettleSheetVo {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotNull(message = "ID不能为空！")
  private String id;
}
