package com.lframework.xingyun.sc.vo.sale.returned;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ApprovePassSaleReturnVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 退单ID
     */
    @NotBlank(message = "退单ID不能为空！")
    private String id;

    /**
     * 备注
     */
    private String description;
}
