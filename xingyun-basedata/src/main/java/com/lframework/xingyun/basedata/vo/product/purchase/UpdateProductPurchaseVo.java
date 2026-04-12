package com.lframework.xingyun.basedata.vo.product.purchase;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateProductPurchaseVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "商品ID不能为空！")
  private String id;

  /**
   * 采购价
   */
  @Schema(description = "采购价", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请输入采购价！")
  @Positive(message = "采购价必须大于0！")
  @Digits(integer = 20, fraction = 6, message = "采购价最多允许6位小数！")
  private BigDecimal price;
}
