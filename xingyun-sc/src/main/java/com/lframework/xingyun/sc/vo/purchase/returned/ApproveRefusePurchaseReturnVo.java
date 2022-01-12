package com.lframework.xingyun.sc.vo.purchase.returned;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ApproveRefusePurchaseReturnVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 退货单ID
     */
    @NotBlank(message = "退货单ID不能为空！")
    private String id;

    /**
     * 拒绝理由
     */
    @NotBlank(message = "拒绝理由不能为空！")
    private String refuseReason;
}
