package com.lframework.xingyun.sc.vo.stock.take.sheet;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TakeStockSheetProductVo implements BaseVo, Serializable {

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
   * 盘点数量
   */
  @Schema(description = "盘点数量", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "盘点数量不能为空！")
  @TypeMismatch(message = "盘点数量格式有误！")
  private BigDecimal takeNum;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
