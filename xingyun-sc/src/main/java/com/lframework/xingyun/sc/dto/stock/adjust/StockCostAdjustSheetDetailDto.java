package com.lframework.xingyun.sc.dto.stock.adjust;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 库存成本调整单明细 Dto
 * </p>
 *
 * @author zmj
 */
@Data
public class StockCostAdjustSheetDetailDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CACHE_NAME = "StockCostAdjustSheetDetailDto";

    /**
     * ID
     */
    private String id;

    /**
     * 单据ID
     */
    private String sheetId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 库存数量
     */
    private Integer stockNum;

    /**
     * 档案采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 调整前成本价
     */
    private BigDecimal oriPrice;

    /**
     * 调整后成本价
     */
    private BigDecimal price;

    /**
     * 库存调价差额
     */
    private BigDecimal diffAmount;

    /**
     * 备注
     */
    private String description;

    /**
     * 排序
     */
    private Integer orderNo;

}
