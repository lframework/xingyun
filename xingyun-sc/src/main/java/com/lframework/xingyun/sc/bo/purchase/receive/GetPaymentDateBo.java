package com.lframework.xingyun.sc.bo.purchase.receive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Data;

@Data
public class GetPaymentDateBo extends BaseBo<GetPaymentDateDto> {

    /**
     * 是否允许修改付款日期
     */
    @Schema(description = "是否允许修改付款日期")
    private Boolean allowModify;

    /**
     * 默认付款日期
     */
    @Schema(description = "默认付款日期")
    @JsonFormat(pattern = StringPool.DATE_PATTERN)
    private LocalDate paymentDate;

    public GetPaymentDateBo() {

    }

    public GetPaymentDateBo(GetPaymentDateDto dto) {

        super(dto);
    }
}
