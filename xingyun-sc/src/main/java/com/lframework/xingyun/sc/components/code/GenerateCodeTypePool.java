package com.lframework.xingyun.sc.components.code;

import com.lframework.starter.web.components.code.GenerateCodeType;

public interface GenerateCodeTypePool {

    /**
     * 采购订单
     */
    GenerateCodeType PURCHASE_ORDER = GenerateCodeType.DEFAULT;

    /**
     * 采购收货单
     */
    GenerateCodeType RECEIVE_SHEET = GenerateCodeType.DEFAULT;

    /**
     * 采购退单
     */
    GenerateCodeType PURCHASE_RETURN = GenerateCodeType.DEFAULT;

    /**
     * 销售订单
     */
    GenerateCodeType SALE_ORDER = GenerateCodeType.DEFAULT;

    /**
     * 销售出库单
     */
    GenerateCodeType SALE_OUT_SHEET = GenerateCodeType.DEFAULT;

    /**
     * 销售退货单
     */
    GenerateCodeType SALE_RETURN = GenerateCodeType.DEFAULT;

    /**
     * 零售出库单
     */
    GenerateCodeType RETAIL_OUT_SHEET = GenerateCodeType.DEFAULT;

    /**
     * 零售退货单
     */
    GenerateCodeType RETAIL_RETURN = GenerateCodeType.DEFAULT;

    /**
     * 批次号
     */
    GenerateCodeType LOT_CODE = GenerateCodeType.DEFAULT;
}
