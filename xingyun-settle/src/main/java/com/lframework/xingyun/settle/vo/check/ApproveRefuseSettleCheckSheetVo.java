package com.lframework.xingyun.settle.vo.check;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ApproveRefuseSettleCheckSheetVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotBlank(message = "ID不能为空！")
    private String id;

    /**
     * 拒绝理由
     */
    @NotBlank(message = "拒绝理由不能为空！")
    private String refuseReason;
}
