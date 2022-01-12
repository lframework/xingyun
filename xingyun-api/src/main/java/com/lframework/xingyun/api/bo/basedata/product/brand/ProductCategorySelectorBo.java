package com.lframework.xingyun.api.bo.basedata.product.brand;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.category.ProductCategoryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCategorySelectorBo extends BaseBo<ProductCategoryDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级ID
     */
    private String parentId;

    /**
     * 状态
     */
    private Boolean available;

    public ProductCategorySelectorBo() {

    }

    public ProductCategorySelectorBo(ProductCategoryDto dto) {

        super(dto);
    }
}
