package com.lframework.xingyun.basedata.bo.product.brand;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductBrandSelectorBo extends BaseBo<ProductBrand> {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 编号
     */
    @Schema(description = "编号")
    private String code;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    public ProductBrandSelectorBo() {

    }

    public ProductBrandSelectorBo(ProductBrand dto) {

        super(dto);
    }
}
