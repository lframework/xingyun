package com.lframework.xingyun.settle.dto.check;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.settle.enums.SettleCheckSheetBizType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SettleCheckBizItemDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单据ID
     */
    private String id;

    /**
     * 单据号
     */
    private String code;

    /**
     * 业务类型
     */
    private SettleCheckSheetBizType bizType;

    /**
     * 对账金额
     */
    private BigDecimal totalAmount;

    /**
     * 审核时间
     */
    private LocalDateTime approveTime;
}
