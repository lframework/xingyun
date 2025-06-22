package com.lframework.xingyun.settle.vo.sheet.customer;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QueryCustomerUnSettleBizItemVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 客户ID
   */
  @ApiModelProperty(value = "客户ID", required = true)
  @NotNull(message = "客户ID不能为空！")
  private String customerId;

  /**
   * 起始时间
   */
  @ApiModelProperty("起始时间")
  private LocalDateTime startTime;

  /**
   * 截至时间
   */
  @ApiModelProperty("截至时间")
  private LocalDateTime endTime;
}
