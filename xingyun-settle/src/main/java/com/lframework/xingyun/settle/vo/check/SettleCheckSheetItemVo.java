package com.lframework.xingyun.settle.vo.check;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.settle.enums.SettleCheckSheetBizType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class SettleCheckSheetItemVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 单据ID
   */
  @ApiModelProperty("单据ID")
  private String id;

  /**
   * 业务类型
   */
  @ApiModelProperty("业务类型")
  @IsEnum(message = "业务类型格式不正确！", enumClass = SettleCheckSheetBizType.class)
  private Integer bizType;

  /**
   * 应付金额
   */
  @ApiModelProperty("应付金额")
  private BigDecimal payAmount;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
