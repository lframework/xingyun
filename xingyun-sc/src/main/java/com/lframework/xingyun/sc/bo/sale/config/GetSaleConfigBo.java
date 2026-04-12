package com.lframework.xingyun.sc.bo.sale.config;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.sc.entity.SaleConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetSaleConfigBo extends BaseBo<SaleConfig> {

    /**
     * 销售出库单是否关联销售订单
     */
    @Schema(description = "销售出库单是否关联销售订单")
    private Boolean outStockRequireSale;

    /**
     * 销售出库单是否多次关联销售订单
     */
    @Schema(description = "销售出库单是否多次关联销售订单")
    private Boolean outStockMultipleRelateSale;

    /**
     * 销售退货单是否关联销售出库单
     */
    @Schema(description = "销售退货单是否关联销售出库单")
    private Boolean saleReturnRequireOutStock;

    /**
     * 销售退货单是否多次关联销售出库单
     */
    @Schema(description = "销售退货单是否多次关联销售出库单")
    private Boolean saleReturnMultipleRelateOutStock;

    /**
     * 销售出库单是否关联物流单
     */
    @Schema(description = "销售出库单是否关联物流单")
    private Boolean outStockRequireLogistics;

    public GetSaleConfigBo() {

    }

    public GetSaleConfigBo(SaleConfig dto) {

        super(dto);
    }
}
