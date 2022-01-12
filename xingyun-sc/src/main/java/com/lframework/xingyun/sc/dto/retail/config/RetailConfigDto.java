package com.lframework.xingyun.sc.dto.retail.config;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class RetailConfigDto implements BaseDto, Serializable {

    public static final String CACHE_NAME = "RetailConfigDto";

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 零售退货单是否关联零售出库单
     */
    private Boolean retailReturnRequireOutStock;

    /**
     * 零售退货单是否多次关联零售出库单
     */
    private Boolean retailReturnMultipleRelateOutStock;
}
