package com.lframework.xingyun.api.bo.basedata.product.property.item;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.property.item.ProductPropertyItemDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductPropertyItemBo extends BaseBo<ProductPropertyItemDto> {

    /**
     * ID
     */
    private String id;

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

    public QueryProductPropertyItemBo() {

    }

    public QueryProductPropertyItemBo(ProductPropertyItemDto dto) {

        super(dto);
    }
}
