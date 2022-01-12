package com.lframework.xingyun.basedata.dto.product.property;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.basedata.enums.ColumnDataType;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.enums.PropertyType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductPropertyModelorDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

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
    private List<ProductPropertyItemModelorDto> items;

    /**
     * 显示值
     * 当columnType != CUSTOM时，此值为items项ID
     * 当columnType == CUSTOM时，此值为录入值
     */
    private String text;

    /**
     * 录入类型
     */
    private ColumnType columnType;

    /**
     * 数据类型
     */
    private ColumnDataType columnDataType;

    /**
     * 属性类别
     */
    private PropertyType propertyType;

    @Data
    public static class ProductPropertyItemModelorDto implements BaseDto, Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * ID
         */
        private String id;

        /**
         * 名称
         */
        private String name;
    }
}
