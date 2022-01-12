package com.lframework.xingyun.sc.entity;

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
 * @since 2021-09-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tbl_purchase_config")
public class PurchaseConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 采购收货单是否关联采购订单
     */
    private Boolean receiveRequirePurchase;

    /**
     * 采购收货单是否多次关联采购订单
     */
    private Boolean receiveMultipleRelatePurchase;

    /**
     * 采购退货单是否关联采购收货单
     */
    private Boolean purchaseReturnRequireReceive;

    /**
     * 采购退货单是否多次关联采购收货单
     */
    private Boolean purchaseReturnMultipleRelateReceive;
}
