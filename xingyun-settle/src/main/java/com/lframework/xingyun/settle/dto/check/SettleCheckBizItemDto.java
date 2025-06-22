package com.lframework.xingyun.settle.dto.check;

import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.xingyun.settle.enums.SettleCheckSheetBizType;
import com.lframework.xingyun.settle.enums.SettleCheckSheetCalcType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SettleCheckBizItemDto implements BaseDto, Serializable {

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
  private SettleCheckSheetBizType bizType;

  /**
   * 计算类型
   */
  private SettleCheckSheetCalcType calcType;

  /**
   * 对账金额
   */
  private BigDecimal totalAmount;

  /**
   * 审核时间
   */
  private LocalDateTime approveTime;
}
