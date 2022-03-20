package com.lframework.xingyun.api.bo.basedata.product.brand;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.brand.ProductBrandDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetProductBrandBo extends BaseBo<ProductBrandDto> {

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
     * 简称
     */
    private String shortName;

    /**
     * logo
     */
    private String logo;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 状态
     */
    private Boolean available;

    /**
     * 备注
     */
    private String description;

    public GetProductBrandBo() {

    }

    public GetProductBrandBo(ProductBrandDto dto) {

        super(dto);
    }
}
