package com.lframework.xingyun.chart.vo;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.chart.enums.OrderChartBizType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderChartVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 单据总金额
   */
  @NotNull(message = "单据总金额不能为空！")
  private BigDecimal totalAmount;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 业务类型
   */
  @NotNull(message = "业务类型不能为空！")
  @IsEnum(message = "业务类型不存在！", enumClass = OrderChartBizType.class)
  private Integer bizType;
}
