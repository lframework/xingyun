package com.lframework.xingyun.sc.vo.stock.adjust;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ApprovePassStockCostAdjustSheetVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotBlank(message = "id不能为空！")
    private String id;

    /**
     * 备注
     */
    private String description;
}
