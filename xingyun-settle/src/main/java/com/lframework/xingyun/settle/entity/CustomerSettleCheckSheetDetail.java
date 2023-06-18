package com.lframework.xingyun.settle.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.settle.enums.CustomerSettleCheckSheetBizType;
import com.lframework.xingyun.settle.enums.CustomerSettleCheckSheetCalcType;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-12-02
 */
@Data
@TableName("customer_settle_check_sheet_detail")
public class CustomerSettleCheckSheetDetail extends BaseEntity implements BaseDto {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 对账单ID
     */
    private String sheetId;

    /**
     * 单据ID
     */
    private String bizId;

    /**
     * 业务类型
     */
    private CustomerSettleCheckSheetBizType bizType;

    /**
     * 计算方式
     */
    private CustomerSettleCheckSheetCalcType calcType;

    /**
     * 应付金额
     */
    private BigDecimal payAmount;

    /**
     * 备注
     */
    private String description;

    /**
     * 排序编号
     */
    private Integer orderNo;


}
