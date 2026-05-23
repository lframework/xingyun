package com.lframework.xingyun.basedata.bo.product.property;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.entity.ProductCategoryPropertyDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryProductCategoryPropertyDefinitionBo extends BaseBo<ProductCategoryPropertyDefinition> {

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
     * 是否必填
     */
    @Schema(description = "是否必填")
    private Boolean isRequired;

    /**
     * 录入类型
     */
    @Schema(description = "录入类型")
    private Integer columnType;

    /**
     * 属性类别
     */
    @Schema(description = "属性类别")
    private Integer propertyType;

    /**
     * 已关联商品分类数量
     */
    @Schema(description = "已关联商品分类数量")
    private Integer categoryCount;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String description;

    public QueryProductCategoryPropertyDefinitionBo() {

    }

    public QueryProductCategoryPropertyDefinitionBo(ProductCategoryPropertyDefinition dto) {

        super(dto);
    }

    @Override
    protected void afterInit(ProductCategoryPropertyDefinition dto) {

        this.columnType = dto.getColumnType().getCode();
        this.propertyType = dto.getPropertyType().getCode();
    }
}
