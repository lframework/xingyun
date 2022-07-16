package com.lframework.xingyun.settle.dto.check.customer;

import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.settle.enums.CustomerSettleCheckSheetBizType;
import com.lframework.xingyun.settle.enums.CustomerSettleCheckSheetCalcType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CustomerSettleCheckBizItemDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 单据ID
   */
  private String id;

  /**
   * 单据号
   */
  private String code;

  /**
   * 业务类型
   */
  private CustomerSettleCheckSheetBizType bizType;

  /**
   * 计算类型
   */
  private CustomerSettleCheckSheetCalcType calcType;

  /**
   * 对账金额
   */
  private BigDecimal totalAmount;

  /**
   * 审核时间
   */
  private LocalDateTime approveTime;
}
