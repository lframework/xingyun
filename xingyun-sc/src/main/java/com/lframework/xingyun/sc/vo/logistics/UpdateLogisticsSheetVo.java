package com.lframework.xingyun.sc.vo.logistics;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateLogisticsSheetVo extends CreateLogisticsSheetVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;
}
