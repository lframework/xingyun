package com.lframework.xingyun.sc.vo.purchase.returned;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
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
  @ApiModelProperty(value = "退货单ID", required = true)
  @NotEmpty(message = "退货单ID不能为空！")
  private List<String> ids;
}
