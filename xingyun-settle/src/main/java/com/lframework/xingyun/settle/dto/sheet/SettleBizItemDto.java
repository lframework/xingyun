package com.lframework.xingyun.settle.dto.sheet;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SettleBizItemDto implements BaseDto, Serializable {

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
   * 未付款金额
   */
  private BigDecimal totalUnPayAmount;

  /**
   * 审核时间
   */
  private LocalDateTime approveTime;
}
