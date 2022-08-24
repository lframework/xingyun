package com.lframework.xingyun.settle.facade.vo.pre;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class SettlePreSheetItemVo implements BaseVo, Serializable {

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
