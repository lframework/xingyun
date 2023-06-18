package com.lframework.xingyun.settle.bo.sheet.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.settle.dto.sheet.customer.CustomerSettleBizItemDto;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CustomerSettleBizItemBo extends BaseBo<CustomerSettleBizItemDto> {

    /**
     * 单据ID
     */
    @ApiModelProperty("单据ID")
    private String id;

    /**
     * 单据号
     */
    @ApiModelProperty("单据号")
    private String code;

    /**
     * 应付金额
     */
    @ApiModelProperty("应付金额")
    private BigDecimal totalPayAmount;

    /**
     * 已付金额
     */
    @ApiModelProperty("已付金额")
    private BigDecimal totalPayedAmount;

    /**
     * 已优惠金额
     */
    @ApiModelProperty("已优惠金额")
    private BigDecimal totalDiscountAmount;

    /**
     * 未付款金额
     */
    @ApiModelProperty("未付款金额")
    private BigDecimal totalUnPayAmount;

    /**
     * 审核时间
     */
    @ApiModelProperty("审核时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime approveTime;

    public CustomerSettleBizItemBo() {

    }

    public CustomerSettleBizItemBo(CustomerSettleBizItemDto dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<CustomerSettleBizItemDto> convert(CustomerSettleBizItemDto dto) {

        return super.convert(dto);
    }

    @Override
    protected void afterInit(CustomerSettleBizItemDto dto) {

    }
}
