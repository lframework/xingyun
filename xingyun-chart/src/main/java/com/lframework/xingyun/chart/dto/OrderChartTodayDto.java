package com.lframework.xingyun.chart.dto;

import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderChartTodayDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 单据总金额
   */
  private BigDecimal totalAmount;

  /**
   * 单据总数量
   */
  private Integer totalNum;

  /**
   * 创建时间
   */
  private String createHour;
}
