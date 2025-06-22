package com.lframework.xingyun.settle.vo.fee.customer;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CustomerSettleFeeSheetItemVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 项目ID
   */
  @ApiModelProperty("项目ID")
  private String id;

  /**
   * 金额
   */
  @ApiModelProperty("金额")
  private BigDecimal amount;
}
