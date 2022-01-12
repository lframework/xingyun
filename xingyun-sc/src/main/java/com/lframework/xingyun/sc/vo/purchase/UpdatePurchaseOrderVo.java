package com.lframework.xingyun.sc.vo.purchase;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UpdatePurchaseOrderVo extends CreatePurchaseOrderVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @NotBlank(message = "订单ID不能为空！")
    private String id;
}
