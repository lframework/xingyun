package com.lframework.xingyun.core.dto.stock;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductStockChangeDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库ID
     */
    private String scId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 变动数量
     */
    private Integer num;

    /**
     * 含税金额
     */
    private BigDecimal taxAmount;

    /**
     * 无税金额
     */
    private BigDecimal unTaxAmount;

    /**
     * 批次变动记录
     */
    private List<ProductLotChangeDto> lotChangeList;
}
