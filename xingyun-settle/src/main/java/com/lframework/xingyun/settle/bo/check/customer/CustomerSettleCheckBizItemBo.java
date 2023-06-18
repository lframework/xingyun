package com.lframework.xingyun.settle.bo.check.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.settle.dto.check.customer.CustomerSettleCheckBizItemDto;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CustomerSettleCheckBizItemBo extends BaseBo<CustomerSettleCheckBizItemDto> {

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
     * 业务类型
     */
    @ApiModelProperty("业务类型")
    private Integer bizType;

    /**
     * 计算类型
     */
    @ApiModelProperty("计算类型")
    private Integer calcType;

    /**
     * 对账金额
     */
    @ApiModelProperty("对账金额")
    private BigDecimal totalAmount;

    /**
     * 审核时间
     */
    @ApiModelProperty("审核时间")
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime approveTime;

    public CustomerSettleCheckBizItemBo() {

    }

    public CustomerSettleCheckBizItemBo(CustomerSettleCheckBizItemDto dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<CustomerSettleCheckBizItemDto> convert(CustomerSettleCheckBizItemDto dto) {

        return super.convert(dto, CustomerSettleCheckBizItemBo::getBizType,
            CustomerSettleCheckBizItemBo::getCalcType);
    }

    @Override
    protected void afterInit(CustomerSettleCheckBizItemDto dto) {

        this.bizType = dto.getBizType().getCode();
        this.calcType = dto.getCalcType().getCode();
    }
}
