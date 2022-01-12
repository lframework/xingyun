package com.lframework.xingyun.sc.vo.sale;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.sc.enums.SaleOrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class SaleOrderSelectorVo extends PageVo {

    private static final long serialVersionUID = 1L;

    /**
     * 单号
     */
    private String code;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 仓库ID
     */
    private String scId;

    /**
     * 操作人ID
     */
    private String createBy;

    /**
     * 操作起始时间
     */
    private LocalDateTime createStartTime;

    /**
     * 操作截止时间
     */
    private LocalDateTime createEndTime;

    /**
     * 状态
     */
    @IsEnum(message = "状态格式不正确！", enumClass = SaleOrderStatus.class)
    private Integer status;
}
