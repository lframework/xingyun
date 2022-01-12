package com.lframework.xingyun.api.bo.basedata.product.property;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyModelorDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductPropertyModelorBo extends BaseBo<ProductPropertyModelorDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否必填
     */
    private Boolean isRequired;

    /**
     * 可选项，当columnType != CUSTOM时 不为空
     */
    private List<ProductPropertyItemModelorBo> items;

    /**
     * 显示值
     * 当columnType != CUSTOM时，此值为items项ID
     * 当columnType == CUSTOM时，此值为录入值
     */
    private String text;

    /**
     * 录入类型
     */
    private Integer columnType;

    /**
     * 数据类型
     */
    private Integer columnDataType;

    /**
     * 属性类别
     */
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
    @EqualsAndHashCode(callSuper = true)
    public static class ProductPropertyItemModelorBo
            extends BaseBo<ProductPropertyModelorDto.ProductPropertyItemModelorDto> {

        private static final long serialVersionUID = 1L;

        /**
         * ID
         */
        private String id;

        /**
         * 名称
         */
        private String name;

        public ProductPropertyItemModelorBo() {

        }

        public ProductPropertyItemModelorBo(ProductPropertyModelorDto.ProductPropertyItemModelorDto dto) {

            super(dto);
        }
    }
}
