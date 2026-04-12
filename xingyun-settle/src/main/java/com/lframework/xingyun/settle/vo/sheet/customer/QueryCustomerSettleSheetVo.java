package com.lframework.xingyun.settle.vo.sheet.customer;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.PageVo;
import com.lframework.xingyun.settle.enums.CustomerSettleSheetStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QueryCustomerSettleSheetVo extends PageVo {

    private static final long serialVersionUID = 1L;

    /**
     * 业务单据号
     */
    @Schema(description = "业务单据号")
    private String code;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createBy;

    /**
     * 创建起始时间
     */
    @Schema(description = "创建起始时间")
    private LocalDateTime createStartTime;

    /**
     * 创建截止时间
     */
    @Schema(description = "创建截止时间")
    private LocalDateTime createEndTime;

    /**
     * 审核人
     */
    @Schema(description = "审核人")
    private String approveBy;

    /**
     * 审核起始时间
     */
    @Schema(description = "审核起始时间")
    private LocalDateTime approveStartTime;

    /**
     * 审核截止时间
     */
    @Schema(description = "审核截止时间")
    private LocalDateTime approveEndTime;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态")
    @IsEnum(message = "审核状态格式不正确！", enumClass = CustomerSettleSheetStatus.class)
    private Integer status;
}
