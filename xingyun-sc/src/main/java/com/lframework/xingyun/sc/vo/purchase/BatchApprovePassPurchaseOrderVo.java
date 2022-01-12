package com.lframework.xingyun.sc.vo.purchase;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class BatchApprovePassPurchaseOrderVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @NotEmpty(message = "订单ID不能为空！")
    private List<String> ids;
}
