package com.lframework.xingyun.sc.vo.purchase.receive;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ReceiveProductVo implements BaseVo, Serializable {

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
     * 收货数量
     */
    private Integer receiveNum;

    /**
     * 备注
     */
    private String description;

    /**
     * 采购订单明细ID
     */
    private String purchaseOrderDetailId;
}
