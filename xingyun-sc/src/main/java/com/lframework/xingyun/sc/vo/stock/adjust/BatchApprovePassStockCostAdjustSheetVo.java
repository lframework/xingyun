package com.lframework.xingyun.sc.vo.stock.adjust;

import com.lframework.starter.web.vo.BaseVo;
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
  @NotEmpty(message = "库存成本调整单ID不能为空！")
  private List<String> ids;
}
