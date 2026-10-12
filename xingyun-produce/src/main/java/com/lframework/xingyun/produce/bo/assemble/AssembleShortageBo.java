package com.lframework.xingyun.produce.bo.assemble;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 缺料计算结果BO
 *
 * @author zmj
 */
@Data
public class AssembleShortageBo {

  @Schema(description = "子件商品ID")
  private String productId;

  @Schema(description = "子件SKU ID")
  private String skuId;

  @Schema(description = "子件SKU编号")
  private String skuCode;

  @Schema(description = "子件商品编号")
  private String productCode;

  @Schema(description = "子件商品名称")
  private String productName;

  @Schema(description = "子件规格")
  private String productSpec;

  @Schema(description = "子件单位")
  private String productUnit;

  @Schema(description = "单位用量")
  private BigDecimal unitNum;

  @Schema(description = "需求数量（= 单位用量 × 组装数量）")
  private BigDecimal requiredNum;

  @Schema(description = "当前库存（指定仓库）")
  private BigDecimal stockNum;

  @Schema(description = "缺口数量（需求 - 库存，不足时为正数；充足为0）")
  private BigDecimal shortageNum;
}
