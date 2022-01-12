package com.lframework.xingyun.settle.vo.fee;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateSettleFeeSheetVo extends CreateSettleFeeSheetVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "ID不能为空！")
    private String id;
}
