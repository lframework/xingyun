package com.lframework.xingyun.sc.dto.purchase.returned;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.PurchaseReturnStatus;
import com.lframework.xingyun.sc.enums.SettleStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PurchaseReturnFullDto implements BaseDto, Serializable {

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
     * 收货单ID
     */
    private String receiveSheetId;

    /**
     * 付款日期
     */
    private LocalDate paymentDate;

    /**
     * 商品数量
     */
    private Integer totalNum;

    /**
     * 赠品数量
     */
    private Integer totalGiftNum;

    /**
     * 退货金额
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
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

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
    private PurchaseReturnStatus status;

    /**
     * 拒绝原因
     */
    private String refuseReason;

    /**
     * 结算状态
     */
    private SettleStatus settleStatus;

    /**
     * 退单明细
     */
    private List<ReturnDetailDto> details;

    @Data
    public static class ReturnDetailDto implements BaseDto, Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 明细ID
         */
        private String id;

        /**
         * 商品ID
         */
        private String productId;

        /**
         * 退货数量
         */
        private Integer returnNum;

        /**
         * 采购价
         */
        private BigDecimal taxPrice;

        /**
         * 是否赠品
         */
        private Boolean isGift;

        /**
         * 税率
         */
        private BigDecimal taxRate;

        /**
         * 备注
         */
        private String description;

        /**
         * 排序编号
         */
        private Integer orderNo;

        /**
         * 收货单明细ID
         */
        private String receiveSheetDetailId;
    }
}
