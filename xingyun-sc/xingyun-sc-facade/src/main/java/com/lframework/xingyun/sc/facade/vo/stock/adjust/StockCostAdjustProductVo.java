package com.lframework.xingyun.sc.facade.vo.stock.adjust;

import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockCostAdjustProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @ApiModelProperty(value = "商品ID", required = true)
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * 调整后成本价
   */
  @ApiModelProperty(value = "调整后成本价", required = true)
  @NotNull(message = "调整后成本价不能为空！")
  @TypeMismatch(message = "调整后成本价格式有误！")
  private BigDecimal price;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
