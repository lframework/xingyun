package com.lframework.xingyun.chart.dto;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderChartSumDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "OrderChartSumDto";

  private static final long serialVersionUID = 1L;

  /**
   * 单据总金额
   */
  private BigDecimal totalAmount;

  /**
   * 单据数量
   */
  private Long totalNum;
}
