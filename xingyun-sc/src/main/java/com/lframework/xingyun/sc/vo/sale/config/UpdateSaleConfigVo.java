package com.lframework.xingyun.sc.vo.sale.config;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateSaleConfigVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 销售出库单是否关联销售订单
     */
    @NotNull(message = "销售出库单是否关联销售订单不能为空！")
    private Boolean outStockRequireSale;

    /**
     * 销售出库单是否多次关联销售订单
     */
    @NotNull(message = "销售出库单是否多次关联销售订单不能为空！")
    private Boolean outStockMultipleRelateSale;

    /**
     * 销售退货单是否关联销售出库单
     */
    @NotNull(message = "销售退货单是否关联销售出库单不能为空！")
    private Boolean saleReturnRequireOutStock;

    /**
     * 销售退货单是否多次关联销售出库单
     */
    @NotNull(message = "销售退货单是否多次关联销售出库单不能为空！")
    private Boolean saleReturnMultipleRelateOutStock;
}
