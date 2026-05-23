package com.lframework.xingyun.sc.vo.stock;

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
public class AddProductStockVo implements BaseVo, Serializable {

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
   * 仓库ID
   */
  @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "仓库ID不能为空！")
  private String scId;

  /**
   * 入库数量
   */
  @Schema(description = "入库数量", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "入库数量不能为空！")
  private BigDecimal stockNum;

  /**
   * 含税成本总金额，如果为null则代表不计算均价入库
   */
  @Schema(description = "含税成本总金额，如果为null则代表不计算均价入库")
  @Min(message = "含税成本总金额不能小于0！", value = 0)
  private BigDecimal taxAmount;

  /**
   * 默认的含税成本总金额，如果不为null则代表：入库时商品没有库存（没有均价），按照此成本金额入库 如果与taxAmount同时为null，那么当入库时没有库存就会报错
   */
  @Schema(description = "默认的含税成本总金额，如果不为null则代表：入库时商品没有库存（没有均价），按照此成本总金额入库 如果与taxAmount同时为null，那么当入库时没有库存就会报错")
  @Min(message = "默认的含税成本总金额不能小于0！", value = 0)
  private BigDecimal defaultTaxAmount;

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
