package com.lframework.xingyun.basedata.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("base_data_product_poly_property")
public class ProductPolyProperty extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 商品聚合ID
     */
    private String polyId;

    /**
     * 商品属性ID
     */
    private String propertyId;

    /**
     * 属性值ID
     */
    private String propertyItemId;

    /**
     * 商品属性值
     */
    private String propertyText;


}
