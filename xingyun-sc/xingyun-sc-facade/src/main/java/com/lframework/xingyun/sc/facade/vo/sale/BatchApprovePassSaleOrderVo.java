package com.lframework.xingyun.sc.facade.vo.sale;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BatchApprovePassSaleOrderVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 订单ID
   */
  @ApiModelProperty(value = "订单ID", required = true)
  @NotEmpty(message = "订单ID不能为空！")
  private List<String> ids;
}
