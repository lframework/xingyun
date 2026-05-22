package com.lframework.xingyun.basedata.bo.product.category;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductCategoryTreeBo extends BaseBo<ProductCategory> {

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

    /**
     * 父级ID
     */
    @Schema(description = "父级ID")
    private String parentId;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String description;

    /**
     * 是否已配置商品分类属性
     */
    @Schema(description = "是否已配置商品分类属性")
    private Boolean hasProperty;

    public ProductCategoryTreeBo() {

    }

    public ProductCategoryTreeBo(ProductCategory dto) {

        super(dto);
    }
}
