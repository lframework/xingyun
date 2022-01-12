package com.lframework.xingyun.basedata.dto.product.poly;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductPolyPropertyDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 聚合ID
     */
    private String polyId;

    /**
     * 属性ID
     */
    private String propertyId;

    /**
     * 属性值ID
     */
    private String propertyItemId;

    /**
     * 属性名称
     */
    private String propertyName;
}
