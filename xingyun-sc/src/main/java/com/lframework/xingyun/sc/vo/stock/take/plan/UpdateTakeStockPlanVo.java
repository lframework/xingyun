package com.lframework.xingyun.sc.vo.stock.take.plan;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UpdateTakeStockPlanVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotBlank(message = "id不能为空！")
    private String id;

    /**
     * 仓库ID
     */
    @NotBlank(message = "请输入仓库ID！")
    private String scId;

    /**
     * 备注
     */
    @NotBlank(message = "请输入备注！")
    private String description;

}
