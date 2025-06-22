package com.lframework.xingyun.sc.bo.sale.config;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.sc.entity.SaleConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetSaleConfigBo extends BaseBo<SaleConfig> {

    /**
     * 销售出库单是否关联销售订单
     */
    @ApiModelProperty("销售出库单是否关联销售订单")
    private Boolean outStockRequireSale;

    /**
     * 销售出库单是否多次关联销售订单
     */
    @ApiModelProperty("销售出库单是否多次关联销售订单")
    private Boolean outStockMultipleRelateSale;

    /**
     * 销售退货单是否关联销售出库单
     */
    @ApiModelProperty("销售退货单是否关联销售出库单")
    private Boolean saleReturnRequireOutStock;

    /**
     * 销售退货单是否多次关联销售出库单
     */
    @ApiModelProperty("销售退货单是否多次关联销售出库单")
    private Boolean saleReturnMultipleRelateOutStock;

    /**
     * 销售出库单是否关联物流单
     */
    @ApiModelProperty("销售出库单是否关联物流单")
    private Boolean outStockRequireLogistics;

    public GetSaleConfigBo() {

    }

    public GetSaleConfigBo(SaleConfig dto) {

        super(dto);
    }
}
