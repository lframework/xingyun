package com.lframework.xingyun.sc.vo.stock.adjust.stock;

import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockAdjustProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @ApiModelProperty(value = "商品ID", required = true)
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * 调整库存数量
   */
  @ApiModelProperty(value = "调整库存数量", required = true)
  @NotNull(message = "调整库存数量不能为空！")
  @TypeMismatch(message = "调整库存数量格式有误！")
  private Integer stockNum;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
