package com.lframework.xingyun.sc.vo.stock.adjust;

import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.vo.BaseVo;
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
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * 调整后成本价
   */
  @NotNull(message = "调整后成本价不能为空！")
  @TypeMismatch(message = "调整后成本价格式有误！")
  private BigDecimal price;

  /**
   * 备注
   */
  private String description;
}
