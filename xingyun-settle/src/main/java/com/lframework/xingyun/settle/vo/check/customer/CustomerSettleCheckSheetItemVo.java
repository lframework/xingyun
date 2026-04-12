package com.lframework.xingyun.settle.vo.check.customer;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.settle.enums.CustomerSettleCheckSheetBizType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CustomerSettleCheckSheetItemVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 单据ID
   */
  @Schema(description = "单据ID")
  private String id;

  /**
   * 业务类型
   */
  @Schema(description = "业务类型")
  @IsEnum(message = "业务类型格式不正确！", enumClass = CustomerSettleCheckSheetBizType.class)
  private Integer bizType;

  /**
   * 应收金额
   */
  @Schema(description = "应收金额")
  private BigDecimal payAmount;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
