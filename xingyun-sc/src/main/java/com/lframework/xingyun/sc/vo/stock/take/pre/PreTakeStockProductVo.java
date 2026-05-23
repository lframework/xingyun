package com.lframework.xingyun.sc.vo.stock.take.pre;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PreTakeStockProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * SKU ID
   */
  @Schema(description = "SKU ID")
  private String skuId;

  /**
   * 初盘数量
   */
  @Schema(description = "初盘数量")
  private BigDecimal firstNum;

  /**
   * 复盘数量
   */
  @Schema(description = "复盘数量")
  private BigDecimal secondNum;

  /**
   * 抽盘数量
   */
  @Schema(description = "抽盘数量")
  private BigDecimal randNum;
}
