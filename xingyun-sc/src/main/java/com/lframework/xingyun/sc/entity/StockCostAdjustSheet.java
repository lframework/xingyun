package com.lframework.xingyun.sc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lframework.xingyun.sc.enums.StockCostAdjustSheetStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 库存成本调整单
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tbl_stock_cost_adjust_sheet")
public class StockCostAdjustSheet extends BaseEntity {

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
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
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

}
