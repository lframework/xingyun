package com.lframework.xingyun.sc.dto.purchase.receive;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.ReceiveSheetStatus;
import com.lframework.xingyun.sc.enums.SettleStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReceiveSheetDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 单号
     */
    private String code;

    /**
     * 仓库ID
     */
    private String scId;

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * 采购员ID
     */
    private String purchaserId;

    /**
     * 付款日期
     */
    private LocalDate paymentDate;

    /**
     * 到货日期
     */
    private LocalDate receiveDate;

    /**
     * 采购单ID
     */
    private String purchaseOrderId;

    /**
     * 商品数量
     */
    private Integer totalNum;

    /**
     * 赠品数量
     */
    private Integer totalGiftNum;

    /**
     * 收货金额
     */
    private BigDecimal totalAmount;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 审核人
     */
    private String approveBy;

    /**
     * 审核时间
     */
    private LocalDateTime approveTime;

    /**
     * 状态
     */
    private ReceiveSheetStatus status;

    /**
     * 结算状态
     */
    private SettleStatus settleStatus;
}
