package com.lframework.xingyun.basedata.bo.product.brand;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductBrandSelectorBo extends BaseBo<ProductBrand> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 编号
     */
    @ApiModelProperty("编号")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Boolean available;

    public ProductBrandSelectorBo() {

    }

    public ProductBrandSelectorBo(ProductBrand dto) {

        super(dto);
    }
}
