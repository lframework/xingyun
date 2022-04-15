package com.lframework.xingyun.sc.vo.stock.take.sheet;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BatchApproveRefuseTakeStockSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotEmpty(message = "ID不能为空！")
  private List<String> ids;

  /**
   * 拒绝原因
   */
  @ApiModelProperty(value = "拒绝原因", required = true)
  @NotBlank(message = "拒绝理由不能为空！")
  private String refuseReason;
}
