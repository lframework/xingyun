package com.lframework.xingyun.sc.vo.stock.take.plan;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateTakeStockPlanVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 仓库ID
   */
  @ApiModelProperty(value = "仓库ID", required = true)
  @NotBlank(message = "请输入仓库ID！")
  private String scId;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  @NotBlank(message = "请输入备注！")
  private String description;

}
