package com.lframework.xingyun.settle.vo.sheet;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SettleSheetItemVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单据ID
     */
    private String id;

    /**
     * 实付金额
     */
    private BigDecimal payAmount;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 备注
     */
    private String description;
}
