package com.lframework.xingyun.basedata.dto.product.poly;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductPolyDto implements BaseDto, Serializable {

    public static final String CACHE_NAME = "ProductPolyDto";

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
     * 简称
     */
    private String shortName;

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
     * 进项税率（%）
     */
    private BigDecimal taxRate;

    /**
     * 销项税率（%）
     */
    private BigDecimal saleTaxRate;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 商品属性
     */
    private List<ProductPolyPropertyDto> properties;
}
