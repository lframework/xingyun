package com.lframework.xingyun.basedata.bo.product.property;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyModelorDto;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class ProductPropertyModelorBo extends BaseBo<ProductPropertyModelorDto> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 是否必填
     */
    @ApiModelProperty("是否必填")
    private Boolean isRequired;

    /**
     * 可选项，当columnType != CUSTOM时 不为空
     */
    @ApiModelProperty("可选项，当columnType != CUSTOM时 不为空")
    private List<ProductPropertyItemModelorBo> items;

    /**
     * 显示值 当columnType != CUSTOM时，此值为items项ID 当columnType == CUSTOM时，此值为录入值
     */
    @ApiModelProperty("显示值 当columnType != CUSTOM时，此值为items项ID 当columnType == CUSTOM时，此值为录入值")
    private String text;

    /**
     * 录入类型
     */
    @ApiModelProperty("录入类型")
    private Integer columnType;

    /**
     * 数据类型
     */
    @ApiModelProperty("数据类型")
    private Integer columnDataType;

    /**
     * 属性类别
     */
    @ApiModelProperty("属性类别")
    private Integer propertyType;

    public ProductPropertyModelorBo() {

    }

    public ProductPropertyModelorBo(ProductPropertyModelorDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<ProductPropertyModelorDto> convert(ProductPropertyModelorDto dto) {

        return super.convert(dto, ProductPropertyModelorBo::getItems, ProductPropertyModelorBo::getColumnType,
                ProductPropertyModelorBo::getColumnDataType, ProductPropertyModelorBo::getPropertyType);
    }

    @Override
    protected void afterInit(ProductPropertyModelorDto dto) {

        if (!CollectionUtil.isEmpty(dto.getItems())) {
            this.items = dto.getItems().stream().map(ProductPropertyItemModelorBo::new).collect(Collectors.toList());
        }

        this.columnType = dto.getColumnType().getCode();
        if (dto.getColumnDataType() != null) {
            this.columnDataType = dto.getColumnDataType().getCode();
        }

        this.propertyType = dto.getPropertyType().getCode();
    }

    @Data
    public static class ProductPropertyItemModelorBo
            extends BaseBo<ProductPropertyModelorDto.ProductPropertyItemModelorDto> {

        private static final long serialVersionUID = 1L;

        /**
         * ID
         */
        @ApiModelProperty("ID")
        private String id;

        /**
         * 名称
         */
        @ApiModelProperty("名称")
        private String name;

        public ProductPropertyItemModelorBo() {

        }

        public ProductPropertyItemModelorBo(ProductPropertyModelorDto.ProductPropertyItemModelorDto dto) {

            super(dto);
        }
    }
}
