package com.lframework.xingyun.sc.vo.purchase.receive;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ApprovePassReceiveSheetVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收货单ID
     */
    @NotBlank(message = "收货单ID不能为空！")
    private String id;

    /**
     * 备注
     */
    private String description;
}
