package com.lframework.xingyun.sc.vo.stock.log;

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
public class AddLogWithAddStockVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 商品ID
   */
  @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "商品ID不能为空！")
  private String productId;

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
   * 含税成本总金额
   */
  @Schema(description = "含税成本总金额", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "含税成本总金额不能为空！")
  @Min(message = "含税成本总金额不能小于0！", value = 0)
  private BigDecimal taxAmount;

  /**
   * 原库存数量
   */
  @Schema(description = "原库存数量", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "原库存数量不能为空！")
  private BigDecimal oriStockNum;

  /**
   * 现库存数量
   */
  @Schema(description = "现库存数量", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "现库存数量不能为空！")
  private BigDecimal curStockNum;

  /**
   * 原含税成本价
   */
  @Schema(description = "原含税成本价", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "原含税成本价不能为空！")
  private BigDecimal oriTaxPrice;

  /**
   * 现含税成本价
   */
  @Schema(description = "现含税成本价", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "现含税成本价不能为空！")
  private BigDecimal curTaxPrice;

  /**
   * 创建人ID
   */
  @Schema(description = "创建人ID")
  private String createById;

  /**
   * 创建人
   */
  @Schema(description = "创建人")
  private String createBy;

  /**
   * 入库时间
   */
  @Schema(description = "入库时间", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "入库时间不能为空！")
  private LocalDateTime createTime;

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
