package com.lframework.xingyun.produce.vo.bom;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BomDetailVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 明细ID（修改时必传）
   */
  @Schema(description = "明细ID")
  private String id;

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
   * 单位用量
   */
  @Schema(description = "单位用量", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "单位用量不能为空！")
  @TypeMismatch(message = "单位用量格式有误！")
  private BigDecimal unitNum;

  /**
   * 损耗率%
   */
  @Schema(description = "损耗率%")
  @TypeMismatch(message = "损耗率格式有误！")
  private BigDecimal scrapRate;

  /**
   * 是否关键物料
   */
  @Schema(description = "是否关键物料")
  private Boolean isKeyMaterial;

  /**
   * 排序
   */
  @Schema(description = "排序")
  private Integer sortOrder;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
