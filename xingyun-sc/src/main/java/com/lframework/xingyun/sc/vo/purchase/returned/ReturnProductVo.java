package com.lframework.xingyun.sc.vo.purchase.returned;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ReturnProductVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 退货数量
     */
    private Integer returnNum;

    /**
     * 备注
     */
    private String description;

    /**
     * 收货单明细ID
     */
    private String receiveSheetDetailId;
}
