package com.lframework.xingyun.sc.vo.purchase.returned;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.sc.enums.PurchaseReturnStatus;
import com.lframework.xingyun.sc.enums.SettleStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QueryPurchaseReturnVo extends PageVo {

    private static final long serialVersionUID = 1L;

    /**
     * 单号
     */
    private String code;

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * 仓库ID
     */
    private String scId;

    /**
     * 操作人ID
     */
    private String createBy;

    /**
     * 操作起始时间
     */
    private LocalDateTime createStartTime;

    /**
     * 操作截止时间
     */
    private LocalDateTime createEndTime;

    /**
     * 审核人ID
     */
    private String approveBy;

    /**
     * 审核起始时间
     */
    private LocalDateTime approveStartTime;

    /**
     * 审核截止时间
     */
    private LocalDateTime approveEndTime;

    /**
     * 状态
     */
    @IsEnum(message = "状态格式不正确！", enumClass = PurchaseReturnStatus.class)
    private Integer status;

    /**
     * 采购员ID
     */
    private String purchaserId;

    /**
     * 采购订单号
     */
    private String receiveSheetCode;

    /**
     * 结算状态
     */
    @IsEnum(message = "结算状态格式不正确！", enumClass = SettleStatus.class)
    private Integer settleStatus;
}
