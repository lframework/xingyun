package com.lframework.xingyun.sc.facade.vo.stock.take.sheet;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApproveRefuseTakeStockSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 拒绝理由
   */
  @ApiModelProperty(value = "拒绝理由", required = true)
  @NotBlank(message = "拒绝理由不能为空！")
  private String refuseReason;
}
