package com.lframework.xingyun.sc.facade.vo.sale.config;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSaleConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 销售出库单是否关联销售订单
   */
  @ApiModelProperty(value = "销售出库单是否关联销售订单", required = true)
  @NotNull(message = "销售出库单是否关联销售订单不能为空！")
  private Boolean outStockRequireSale;

  /**
   * 销售出库单是否多次关联销售订单
   */
  @ApiModelProperty(value = "销售出库单是否多次关联销售订单", required = true)
  @NotNull(message = "销售出库单是否多次关联销售订单不能为空！")
  private Boolean outStockMultipleRelateSale;

  /**
   * 销售退货单是否关联销售出库单
   */
  @ApiModelProperty(value = "销售退货单是否关联销售出库单", required = true)
  @NotNull(message = "销售退货单是否关联销售出库单不能为空！")
  private Boolean saleReturnRequireOutStock;

  /**
   * 销售退货单是否多次关联销售出库单
   */
  @ApiModelProperty(value = "销售退货单是否多次关联销售出库单", required = true)
  @NotNull(message = "销售退货单是否多次关联销售出库单不能为空！")
  private Boolean saleReturnMultipleRelateOutStock;
}
