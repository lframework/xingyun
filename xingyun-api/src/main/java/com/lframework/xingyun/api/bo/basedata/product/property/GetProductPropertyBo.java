package com.lframework.xingyun.api.bo.basedata.product.property;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.category.ProductCategoryDto;
import com.lframework.xingyun.basedata.dto.product.category.property.ProductCategoryPropertyDto;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyDto;
import com.lframework.xingyun.basedata.enums.PropertyType;
import com.lframework.xingyun.basedata.service.product.IProductCategoryPropertyService;
import com.lframework.xingyun.basedata.service.product.IProductCategoryService;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetProductPropertyBo extends BaseBo<ProductPropertyDto> {

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
     * 是否必填
     */
    private Boolean isRequired;

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

    /**
     * 类目
     */
    private List<CategoryBo> categories;

    /**
     * 状态
     */
    private Boolean available;

    /**
     * 备注
     */
    private String description;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class CategoryBo extends BaseBo<ProductCategoryDto> {

        /**
         * 类目ID
         */
        private String id;

        /**
         * 类目名称
         */
        private String name;

        public CategoryBo() {

        }

        public CategoryBo(ProductCategoryDto dto) {

            super(dto);
        }
    }

    public GetProductPropertyBo() {

    }

    public GetProductPropertyBo(ProductPropertyDto dto) {

        super(dto);
    }

    @Override
    protected void afterInit(ProductPropertyDto dto) {

        this.columnType = dto.getColumnType().getCode();
        this.propertyType = dto.getPropertyType().getCode();
        if (dto.getColumnDataType() != null) {
            this.columnDataType = dto.getColumnDataType().getCode();
        }

        if (dto.getPropertyType() == PropertyType.APPOINT) {
            IProductCategoryPropertyService productCategoryPropertyService = ApplicationUtil
                    .getBean(IProductCategoryPropertyService.class);
            List<ProductCategoryPropertyDto> categoryPropertyList = productCategoryPropertyService
                    .getByPropertyId(dto.getId());

            IProductCategoryService productCategoryService = ApplicationUtil.getBean(IProductCategoryService.class);
            this.categories = categoryPropertyList.stream().map(t -> productCategoryService.getById(t.getCategoryId()))
                    .map(CategoryBo::new).collect(Collectors.toList());
        }
    }
}
