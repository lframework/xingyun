package com.lframework.xingyun.sc.dto.stock;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductLotStockDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 仓库ID
     */
    private String scId;

    /**
     * 批次ID
     */
    private String lotId;


    /**
     * 库存数量
     */
    private Integer stockNum;
}
