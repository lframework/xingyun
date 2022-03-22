package com.lframework.xingyun.settle.vo.pre;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BatchApprovePassSettlePreSheetVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @NotNull(message = "费用单ID不能为空！")
  private List<String> ids;
}
