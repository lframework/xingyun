package com.lframework.xingyun.sc.dto.stock.take.plan;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TakeStockPlanDetailDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 初始库存数量
     */
    private Integer stockNum;

    /**
     * 修改后的盘点数量
     */
    private Integer takeNum;

    /**
     * 出项数量
     */
    private Integer totalOutNum;

    /**
     * 入项数量
     */
    private Integer totalInNum;

    /**
     * 备注
     */
    private String description;

    /**
     * 排序
     */
    private Integer orderNo;
}
