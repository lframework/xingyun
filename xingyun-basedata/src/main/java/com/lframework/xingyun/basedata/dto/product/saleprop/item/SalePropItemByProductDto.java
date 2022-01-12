package com.lframework.xingyun.basedata.dto.product.saleprop.item;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class SalePropItemByProductDto implements BaseDto, Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 销售属性组ID
     */
    private String groupId;

    /**
     * 销售属性组名称
     */
    private String groupName;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 排序编号
     */
    private Integer orderNo;
}
