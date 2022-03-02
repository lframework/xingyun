package com.lframework.xingyun.sc.vo.stock.take.plan;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CancelTakeStockPlanVo implements BaseVo, Serializable {
private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotBlank(message = "ID不能为空！")
    private String id;
}
