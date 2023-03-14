package com.lframework.xingyun.sc.bo.purchase.receive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetPaymentDateBo extends BaseBo<GetPaymentDateDto> {

    /**
     * 是否允许修改付款日期
     */
    @ApiModelProperty("是否允许修改付款日期")
    private Boolean allowModify;

    /**
     * 默认付款日期
     */
    @ApiModelProperty("默认付款日期")
    @JsonFormat(pattern = StringPool.DATE_PATTERN)
    private LocalDate paymentDate;

    public GetPaymentDateBo() {

    }

    public GetPaymentDateBo(GetPaymentDateDto dto) {

        super(dto);
    }
}
