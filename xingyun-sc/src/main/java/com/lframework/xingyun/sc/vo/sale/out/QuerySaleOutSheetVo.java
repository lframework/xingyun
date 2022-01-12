package com.lframework.xingyun.sc.vo.sale.out;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.sc.enums.SaleOutSheetStatus;
import com.lframework.xingyun.sc.enums.SettleStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuerySaleOutSheetVo extends PageVo {

    private static final long serialVersionUID = 1L;

    /**
     * 单号
     */
    private String code;

    /**
     * 客户ID
     */
    private String customerId;

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
    @IsEnum(message = "状态格式不正确！", enumClass = SaleOutSheetStatus.class)
    private Integer status;

    /**
     * 销售员ID
     */
    private String salerId;

    /**
     * 销售订单号
     */
    private String saleOrderCode;

    /**
     * 结算状态
     */
    @IsEnum(message = "结算状态格式不正确！", enumClass = SettleStatus.class)
    private Integer settleStatus;
}
