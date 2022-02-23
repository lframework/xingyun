package com.lframework.xingyun.sc.vo.stock.take.pre;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdatePreTakeStockSheetVo extends CreatePreTakeStockSheetVo {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotBlank(message = "id不能为空！")
    private String id;
}
