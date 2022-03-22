package com.lframework.xingyun.sc.vo.purchase.returned;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BatchApprovePassPurchaseReturnVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 退货单ID
   */
  @NotEmpty(message = "退货单ID不能为空！")
  private List<String> ids;
}
