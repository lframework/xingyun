package com.lframework.xingyun.settle.facade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-12-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("settle_sheet_detail")
public class SettleSheetDetail extends BaseEntity implements BaseDto {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 结算单ID
     */
    private String sheetId;

    /**
     * 单据ID
     */
    private String bizId;

    /**
     * 实付金额
     */
    private BigDecimal payAmount;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 备注
     */
    private String description;

    /**
     * 排序编号
     */
    private Integer orderNo;


}
