package com.lframework.xingyun.sc.vo.stock.lot;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AddProductLotStockVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 批次ID
     */
    @NotBlank(message = "批次ID不能为空！")
    private String lotId;

    /**
     * 仓库ID
     */
    @NotBlank(message = "仓库ID不能为空！")
    private String scId;

    /**
     * 入库数量
     */
    @NotNull(message = "入库数量不能为空！")
    @Min(message = "入库数量必须大于0！", value = 1)
    private Integer stockNum;
}
