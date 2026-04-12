package com.lframework.xingyun.basedata.vo.product.sale;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateProductSaleVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @Schema(description = "商品ID")
  private String id;

  /**
   * 销售价
   */
  @Schema(description = "销售价", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请输入销售价！")
  @Positive(message = "销售价必须大于0！")
  @Digits(integer = 20, fraction = 6, message = "销售价最多允许6位小数！")
  private BigDecimal price;
}
