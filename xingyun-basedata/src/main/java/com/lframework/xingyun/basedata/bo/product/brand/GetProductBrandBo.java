package com.lframework.xingyun.basedata.bo.product.brand;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductBrand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetProductBrandBo extends BaseBo<ProductBrand> {

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
     * 简称
     */
    @ApiModelProperty("简称")
    private String shortName;

    /**
     * logo
     */
    @ApiModelProperty("logo")
    private String logo;

    /**
     * 简介
     */
    @ApiModelProperty("简介")
    private String introduction;

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

    public GetProductBrandBo() {

    }

    public GetProductBrandBo(ProductBrand dto) {

        super(dto);
    }
}
