package com.lframework.xingyun.api.bo.basedata.product.saleprop.item;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.saleprop.item.ProductSalePropItemDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetEnableSalePropItemBo extends BaseBo<ProductSalePropItemDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    public GetEnableSalePropItemBo() {

    }

    public GetEnableSalePropItemBo(ProductSalePropItemDto dto) {

        super(dto);
    }
}
