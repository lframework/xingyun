package com.lframework.xingyun.api.bo.basedata.product.saleprop.item;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductSalePropItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetEnableSalePropItemBo extends BaseBo<ProductSalePropItem> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    public GetEnableSalePropItemBo() {

    }

    public GetEnableSalePropItemBo(ProductSalePropItem dto) {

        super(dto);
    }
}
