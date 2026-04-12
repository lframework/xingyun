package com.lframework.xingyun.sc.vo.stock.transfer;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScTransferProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * 调拨数量
   */
  @Schema(description = "调拨数量", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "调拨数量不能为空！")
  @TypeMismatch(message = "调拨数量格式有误！")
  private BigDecimal transferNum;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
