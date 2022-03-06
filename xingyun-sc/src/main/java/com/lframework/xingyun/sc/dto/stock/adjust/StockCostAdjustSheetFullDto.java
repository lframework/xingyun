package com.lframework.xingyun.sc.dto.stock.adjust;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.StockCostAdjustSheetStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 库存成本调整单详情 Dto
 * </p>
 *
 * @author zmj
 */
@Data
public class StockCostAdjustSheetFullDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 业务单据号
     */
    private String code;

    /**
     * 仓库ID
     */
    private String scId;

    /**
     * 调价品种数
     */
    private Integer productNum;

    /**
     * 库存调价差额
     */
    private BigDecimal diffAmount;

    /**
     * 状态
     */
    private StockCostAdjustSheetStatus status;

    /**
     * 备注
     */
    private String description;

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
     * 拒绝原因
     */
    private String refuseReason;

    /**
     * 明细
     */
    private List<DetailDto> details;

    @Data
    public static class DetailDto implements BaseDto, Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * ID
         */
        private String id;

        /**
         * 商品ID
         */
        private String productId;

        /**
         * 档案采购价
         */
        private BigDecimal purchasePrice;

        /**
         * 库存数量
         */
        private Integer stockNum;

        /**
         * 调整前成本价
         */
        private BigDecimal oriPrice;

        /**
         * 调整后成本价
         */
        private BigDecimal price;

        /**
         * 库存调价差额
         */
        private BigDecimal diffAmount;

        /**
         * 备注
         */
        private String description;
    }

}
