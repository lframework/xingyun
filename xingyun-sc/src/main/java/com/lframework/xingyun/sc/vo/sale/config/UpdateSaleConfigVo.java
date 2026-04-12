package com.lframework.xingyun.sc.vo.sale.config;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSaleConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 销售出库单是否关联销售订单
   */
  @Schema(description = "销售出库单是否关联销售订单", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "销售出库单是否关联销售订单不能为空！")
  private Boolean outStockRequireSale;

  /**
   * 销售出库单是否多次关联销售订单
   */
  @Schema(description = "销售出库单是否多次关联销售订单", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "销售出库单是否多次关联销售订单不能为空！")
  private Boolean outStockMultipleRelateSale;

  /**
   * 销售退货单是否关联销售出库单
   */
  @Schema(description = "销售退货单是否关联销售出库单", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "销售退货单是否关联销售出库单不能为空！")
  private Boolean saleReturnRequireOutStock;

  /**
   * 销售退货单是否多次关联销售出库单
   */
  @Schema(description = "销售退货单是否多次关联销售出库单", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "销售退货单是否多次关联销售出库单不能为空！")
  private Boolean saleReturnMultipleRelateOutStock;

  /**
   * 销售出库单是否关联物流单
   */
  @Schema(description = "销售出库单是否关联物流单", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "销售出库单是否关联物流单不能为空！")
  private Boolean outStockRequireLogistics;
}
