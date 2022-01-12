package com.lframework.xingyun.sc.dto.sale.config;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SaleConfigDto implements BaseDto, Serializable {

    public static final String CACHE_NAME = "SaleConfigDto";

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 销售出库单是否关联销售订单
     */
    private Boolean outStockRequireSale;

    /**
     * 销售出库单是否多次关联销售订单
     */
    private Boolean outStockMultipleRelateSale;

    /**
     * 销售退货单是否关联销售出库单
     */
    private Boolean saleReturnRequireOutStock;

    /**
     * 销售退货单是否多次关联销售出库单
     */
    private Boolean saleReturnMultipleRelateOutStock;
}
