package com.lframework.xingyun.basedata.dto.product.retail;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductRetailDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 零售价
     */
    private BigDecimal price;
}
