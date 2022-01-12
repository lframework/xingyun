package com.lframework.xingyun.api.bo.basedata.product.saleprop;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.saleprop.ProductSalePropGroupDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSalePropGroupSelectorBo extends BaseBo<ProductSalePropGroupDto> {

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

    public ProductSalePropGroupSelectorBo() {

    }

    public ProductSalePropGroupSelectorBo(ProductSalePropGroupDto dto) {

        super(dto);
    }
}
