package com.lframework.xingyun.basedata.vo.product.purchase;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CreateProductPurchaseVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private String id;

    /**
     * 采购价
     */
    @NotNull(message = "请输入采购价！")
    @Positive(message = "采购价必须大于0！")
    @Digits(integer = 20, fraction = 2, message = "采购价最多允许2位小数！")
    private BigDecimal price;
}
