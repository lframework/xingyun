package com.lframework.xingyun.produce.vo.assemble;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 组装单子件VO
 *
 * @author zmj
 */
@Data
public class AssembleProductVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 子件商品ID
   */
  @Schema(description = "子件商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "子件商品不能为空！")
  private String productId;

  /**
   * 子件SKU ID
   */
  @Schema(description = "子件SKU ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "子件SKU不能为空！")
  private String skuId;

  /**
   * 单位用量（自BOM带出）
   */
  @Schema(description = "单位用量")
  @NotNull(message = "单位用量不能为空！")
  private BigDecimal num;

  /**
   * 本次消耗数量
   */
  @Schema(description = "本次消耗数量", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "消耗数量不能为空！")
  private BigDecimal stockNum;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
