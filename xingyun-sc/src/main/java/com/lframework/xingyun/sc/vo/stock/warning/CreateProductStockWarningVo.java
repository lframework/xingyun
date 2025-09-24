package com.lframework.xingyun.sc.vo.stock.warning;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateProductStockWarningVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 仓库ID
   */
  @ApiModelProperty(value = "仓库ID", required = true)
  @NotBlank(message = "仓库ID不能为空！")
  private String scId;

  /**
   * 商品ID
   */
  @ApiModelProperty(value = "商品ID", required = true)
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * 预警下限
   */
  @ApiModelProperty(value = "预警下限", required = true)
  @NotNull(message = "预警下限不能为空！")
  @Positive(message = "预警下限必须大于0！")
  @Digits(integer = 20, fraction = 8, message = "预警下限最多允许8位小数！")
  private BigDecimal minLimit;

  /**
   * 预警上限
   */
  @ApiModelProperty(value = "预警上限", required = true)
  @NotNull(message = "预警上限不能为空！")
  @Positive(message = "预警上限必须大于0！")
  @Digits(integer = 20, fraction = 8, message = "预警上限最多允许8位小数！")
  private BigDecimal maxLimit;
}
