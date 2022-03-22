package com.lframework.xingyun.sc.vo.stock.take.sheet;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BatchApprovePassTakeStockSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @NotEmpty(message = "ID不能为空！")
  private List<String> ids;
}
