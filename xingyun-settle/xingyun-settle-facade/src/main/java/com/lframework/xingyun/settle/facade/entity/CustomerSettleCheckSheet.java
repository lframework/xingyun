package com.lframework.xingyun.settle.facade.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.settle.facade.enums.CustomerSettleCheckSheetStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("customer_settle_check_sheet")
public class CustomerSettleCheckSheet extends BaseEntity implements BaseDto {

    private static final long serialVersionUID = 1L;

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
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 应付金额
     */
    private BigDecimal totalPayAmount;

    /**
     * 已付金额
     */
    private BigDecimal totalPayedAmount;

    /**
     * 已优惠金额
     */
    private BigDecimal totalDiscountAmount;

    /**
     * 起始时间
     */
    private LocalDate startDate;

    /**
     * 截止日期
     */
    private LocalDate endDate;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建人ID 新增时赋值
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间 新增时赋值
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人ID 新增和修改时赋值
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

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
    private CustomerSettleCheckSheetStatus status;

    /**
     * 拒绝原因
     */
    private String refuseReason;

    /**
     * 结算状态
     */
    private SettleStatus settleStatus;


}
