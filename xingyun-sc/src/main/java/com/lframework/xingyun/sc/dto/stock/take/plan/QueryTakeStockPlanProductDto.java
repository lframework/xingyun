package com.lframework.xingyun.sc.dto.stock.take.plan;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class QueryTakeStockPlanProductDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 初始库存
     */
    private Integer stockNum;

    /**
     * 出项数量
     */
    private Integer totalOutNum;

    /**
     * 入项数量
     */
    private Integer totalInNum;
}
