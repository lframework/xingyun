package com.lframework.xingyun.basedata.bo.product.property.item;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetProductCategoryPropertyItemBo extends BaseBo<ProductCategoryPropertyItem> {

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
     * 备注
     */
    @Schema(description = "备注")
    private String description;

    public GetProductCategoryPropertyItemBo() {

    }

    public GetProductCategoryPropertyItemBo(ProductCategoryPropertyItem dto) {

        super(dto);
    }
}
