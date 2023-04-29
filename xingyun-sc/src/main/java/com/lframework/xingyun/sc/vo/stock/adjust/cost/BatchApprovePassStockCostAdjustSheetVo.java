package com.lframework.xingyun.sc.vo.stock.adjust.cost;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BatchApprovePassStockCostAdjustSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 库存成本调整单ID
   */
  @ApiModelProperty(value = "库存成本调整单ID", required = true)
  @NotEmpty(message = "库存成本调整单ID不能为空！")
  private List<String> ids;
}
