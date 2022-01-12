package com.lframework.xingyun.basedata.dto.product.info;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.basedata.enums.ColumnType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class GetProductDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

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
     * 类目ID
     */
    private String categoryId;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 品牌ID
     */
    private String brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 是否多销售属性
     */
    private Boolean multiSaleProp;

    /**
     * SKU
     */
    private String skuCode;

    /**
     * 外部编号
     */
    private String externalCode;

    /**
     * 规格
     */
    private String spec;

    /**
     * 单位
     */
    private String unit;

    /**
     * 采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 销售价
     */
    private BigDecimal salePrice;

    /**
     * 零售价
     */
    private BigDecimal retailPrice;

    /**
     * 状态
     */
    private Boolean available;

    /**
     * 属性
     */
    private List<PropertyDto> properties;

    @Data
    public static class PropertyDto implements BaseDto, Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 属性ID
         */
        private String id;

        /**
         * 属性名
         */
        private String name;

        /**
         * 字段类型
         */
        private ColumnType columnType;

        /**
         * 属性文本
         */
        private String text;
    }
}
