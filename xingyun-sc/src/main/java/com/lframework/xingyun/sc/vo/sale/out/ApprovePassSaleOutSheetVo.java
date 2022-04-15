package com.lframework.xingyun.sc.vo.sale.out;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApprovePassSaleOutSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 出库单ID
   */
  @ApiModelProperty(value = "出库单ID", required = true)
  @NotBlank(message = "出库单ID不能为空！")
  private String id;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
