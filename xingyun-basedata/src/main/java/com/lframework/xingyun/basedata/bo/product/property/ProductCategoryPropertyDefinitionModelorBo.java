package com.lframework.xingyun.basedata.bo.product.property;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.property.ProductCategoryPropertyDefinitionModelorDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class ProductCategoryPropertyDefinitionModelorBo extends BaseBo<ProductCategoryPropertyDefinitionModelorDto> {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

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
     * 可选项，当columnType != CUSTOM时 不为空
     */
    @Schema(description = "可选项，当columnType != CUSTOM时 不为空")
    private List<ProductCategoryPropertyItemModelorBo> items;

    /**
     * 显示值 当columnType != CUSTOM时，此值为items项ID 当columnType == CUSTOM时，此值为录入值
     */
    @Schema(description = "显示值 当columnType != CUSTOM时，此值为items项ID 当columnType == CUSTOM时，此值为录入值")
    private String text;

    /**
     * 录入类型
     */
    @Schema(description = "录入类型")
    private Integer columnType;

    /**
     * 数据类型
     */
    @Schema(description = "数据类型")
    private Integer columnDataType;

    /**
     * 属性类别
     */
    @Schema(description = "属性类别")
    private Integer propertyType;

    public ProductCategoryPropertyDefinitionModelorBo() {

    }

    public ProductCategoryPropertyDefinitionModelorBo(ProductCategoryPropertyDefinitionModelorDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<ProductCategoryPropertyDefinitionModelorDto> convert(ProductCategoryPropertyDefinitionModelorDto dto) {

        return super.convert(dto, ProductCategoryPropertyDefinitionModelorBo::getItems, ProductCategoryPropertyDefinitionModelorBo::getColumnType,
                ProductCategoryPropertyDefinitionModelorBo::getColumnDataType, ProductCategoryPropertyDefinitionModelorBo::getPropertyType);
    }

    @Override
    protected void afterInit(ProductCategoryPropertyDefinitionModelorDto dto) {

        if (!CollectionUtil.isEmpty(dto.getItems())) {
            this.items = dto.getItems().stream().map(ProductCategoryPropertyItemModelorBo::new).collect(Collectors.toList());
        }

        this.columnType = dto.getColumnType().getCode();
        if (dto.getColumnDataType() != null) {
            this.columnDataType = dto.getColumnDataType().getCode();
        }

        this.propertyType = dto.getPropertyType().getCode();
    }

    @Data
    public static class ProductCategoryPropertyItemModelorBo
            extends BaseBo<ProductCategoryPropertyDefinitionModelorDto.ProductCategoryPropertyItemModelorDto> {

        private static final long serialVersionUID = 1L;

        /**
         * ID
         */
        @Schema(description = "ID")
        private String id;

        /**
         * 名称
         */
        @Schema(description = "名称")
        private String name;

        public ProductCategoryPropertyItemModelorBo() {

        }

        public ProductCategoryPropertyItemModelorBo(ProductCategoryPropertyDefinitionModelorDto.ProductCategoryPropertyItemModelorDto dto) {

            super(dto);
        }
    }
}
