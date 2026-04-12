package com.lframework.xingyun.sc.vo.stock.lot;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductLotVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

  /**
   * 供应商ID
   */
  @Schema(description = "供应商ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "供应商ID不能为空！")
  private String supplierId;

  /**
   * 税率（%）
   */
  @Schema(description = "税率（%）", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "税率（%）不能为空！")
  @Min(message = "税率（%）不能小于0！", value = 0)
  private BigDecimal taxRate;

  /**
   * 入库时间
   */
  @Schema(description = "入库时间")
  private LocalDateTime createTime = LocalDateTime.now();

  /**
   * 业务单据ID
   */
  @Schema(description = "业务单据ID")
  private String bizId;

  /**
   * 业务单据明细ID
   */
  @Schema(description = "业务单据明细ID")
  private String bizDetailId;

  /**
   * 业务单据号
   */
  @Schema(description = "业务单据号")
  private String bizCode;

  /**
   * 业务类型
   */
  @Schema(description = "业务类型", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "业务类型不能为空！")
  @IsEnum(message = "业务类型不正确！", enumClass = ProductStockBizType.class)
  private Integer bizType;
}
