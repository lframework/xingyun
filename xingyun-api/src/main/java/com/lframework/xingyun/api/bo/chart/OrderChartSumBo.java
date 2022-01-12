package com.lframework.xingyun.api.bo.chart;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.chart.dto.OrderChartSumDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderChartSumBo extends BaseBo<OrderChartSumDto> {

    /**
     * 单据总金额
     */
    private BigDecimal totalAmount;

    /**
     * 单据数量
     */
    private Long totalNum;

    public OrderChartSumBo() {

    }

    public OrderChartSumBo(OrderChartSumDto dto) {

        super(dto);
    }
}
