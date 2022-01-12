package com.lframework.xingyun.settle.vo.pre;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.enums.SettlePreSheetStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySettlePreSheetVo extends PageVo {

    private static final long serialVersionUID = 1L;

    /**
     * 业务单据号
     */
    private String code;

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建起始时间
     */
    private LocalDateTime createStartTime;

    /**
     * 创建截止时间
     */
    private LocalDateTime createEndTime;

    /**
     * 审核人
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
     * 审核状态
     */
    @IsEnum(message = "审核状态格式不正确！", enumClass = SettlePreSheetStatus.class)
    private Integer status;

    /**
     * 结算状态
     */
    @IsEnum(message = "结算状态格式不正确！", enumClass = SettleStatus.class)
    private Integer settleStatus;
}
