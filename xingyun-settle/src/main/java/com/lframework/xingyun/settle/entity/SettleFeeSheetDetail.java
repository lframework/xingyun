package com.lframework.xingyun.settle.entity;

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
 * @since 2021-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("settle_fee_sheet_detail")
public class SettleFeeSheetDetail extends BaseEntity implements BaseDto {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 费用单ID
     */
    private String sheetId;

    /**
     * 项目ID
     */
    private String itemId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 排序编号
     */
    private Integer orderNo;


}
