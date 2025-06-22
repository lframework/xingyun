package com.lframework.xingyun.basedata.bo.product.category;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductCategoryTreeBo extends BaseBo<ProductCategory> {

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
     * 父级ID
     */
    @ApiModelProperty("父级ID")
    private String parentId;

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

    public ProductCategoryTreeBo() {

    }

    public ProductCategoryTreeBo(ProductCategory dto) {

        super(dto);
    }
}
