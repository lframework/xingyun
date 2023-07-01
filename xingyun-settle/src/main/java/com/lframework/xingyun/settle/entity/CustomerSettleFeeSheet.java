package com.lframework.xingyun.settle.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.enums.SettleStatus;
import com.lframework.xingyun.settle.enums.CustomerSettleFeeSheetStatus;
import com.lframework.xingyun.settle.enums.CustomerSettleFeeSheetType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-11-30
 */
@Data
@TableName("customer_settle_fee_sheet")
public class CustomerSettleFeeSheet extends BaseEntity implements BaseDto {

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
     * 客户ID
     */
    private String customerId;

    /**
     * 单据类型
     */
    private CustomerSettleFeeSheetType sheetType;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建人ID 新增时赋值
     */
    @TableField(fill = FieldFill.INSERT)
    private String createById;

    /**
     * 创建人 新增时赋值
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间 新增时赋值
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人 新增和修改时赋值
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 修改人ID 新增和修改时赋值
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateById;

    /**
     * 修改时间 新增和修改时赋值
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
     * 状态
     */
    private CustomerSettleFeeSheetStatus status;

    /**
     * 拒绝原因
     */
    private String refuseReason;

    /**
     * 结算状态
     */
    private SettleStatus settleStatus;
}
