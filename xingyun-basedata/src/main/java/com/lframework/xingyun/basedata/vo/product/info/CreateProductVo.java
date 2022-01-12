package com.lframework.xingyun.basedata.vo.product.info;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateProductVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotBlank(message = "请输入编号！")
    private String code;

    /**
     * 名称
     */
    @NotBlank(message = "请输入名称！")
    private String name;

    /**
     * polyID
     */
    @NotBlank(message = "PolyId不能为空！")
    private String polyId;

    /**
     * 商品SKU编号
     */
    @NotBlank(message = "商品SKU编号不能为空！")
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
     * 销售属性ID
     */
    private List<String> salePropItems;
}
