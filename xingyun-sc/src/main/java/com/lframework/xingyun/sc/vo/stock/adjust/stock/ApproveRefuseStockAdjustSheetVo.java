package com.lframework.xingyun.sc.vo.stock.adjust.stock;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApproveRefuseStockAdjustSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty("ID")
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 拒绝理由
   */
  @ApiModelProperty("拒绝理由")
  @NotBlank(message = "拒绝理由不能为空！")
  private String refuseReason;
}
