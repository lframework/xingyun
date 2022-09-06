package com.lframework.xingyun.sc.dto.stock.adjust;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class StockCostAdjustDiffDto implements Serializable, BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * 库存数量
   */
  private Integer stockNum = 0;

  /**
   * 原价
   */
  private BigDecimal oriPrice = BigDecimal.ZERO;

  /**
   * 调整金额
   */
  private BigDecimal diffAmount = BigDecimal.ZERO;
}
