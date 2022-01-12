package com.lframework.xingyun.basedata.dto.product.saleprop.item;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductSalePropItemDto implements BaseDto, Serializable {

    public static final String CACHE_NAME = "ProductSalePropItemDto";

    public static final String CACHE_NAME_BY_PRODUCT_ID = "ProductSalePropItemDtoByProductId";

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 销售属性组ID
     */
    private String groupId;

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private Boolean available;

    /**
     * 备注
     */
    private String description;
}
