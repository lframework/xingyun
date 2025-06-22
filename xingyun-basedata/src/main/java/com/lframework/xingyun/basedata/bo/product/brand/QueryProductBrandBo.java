package com.lframework.xingyun.basedata.bo.product.brand;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryProductBrandBo extends BaseBo<ProductBrand> {

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

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    public QueryProductBrandBo() {

    }

    public QueryProductBrandBo(ProductBrand dto) {

        super(dto);
    }
}
