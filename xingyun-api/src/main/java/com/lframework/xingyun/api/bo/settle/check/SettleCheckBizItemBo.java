package com.lframework.xingyun.api.bo.settle.check;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.settle.dto.check.SettleCheckBizItemDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class SettleCheckBizItemBo extends BaseBo<SettleCheckBizItemDto> {

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
    private Integer bizType;

    /**
     * 对账金额
     */
    private BigDecimal totalAmount;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime approveTime;

    public SettleCheckBizItemBo() {

    }

    public SettleCheckBizItemBo(SettleCheckBizItemDto dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<SettleCheckBizItemDto> convert(SettleCheckBizItemDto dto) {

        return super.convert(dto, SettleCheckBizItemBo::getBizType);
    }

    @Override
    protected void afterInit(SettleCheckBizItemDto dto) {

        this.bizType = dto.getBizType().getCode();
    }
}
