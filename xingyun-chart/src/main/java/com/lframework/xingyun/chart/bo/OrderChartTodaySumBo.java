package com.lframework.xingyun.chart.bo;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.chart.dto.OrderChartSumDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class OrderChartTodaySumBo extends BaseBo<OrderChartSumDto> {

    /**
     * 单据总金额
     */
    @Schema(description = "单据总金额")
    private BigDecimal totalAmount;

    /**
     * 单据数量
     */
    @Schema(description = "单据数量")
    private Long totalNum;

    /**
     * 图表数据
     */
    @Schema(description = "图表数据")
    private List<OrderChartTodayBo> charts;

    public OrderChartTodaySumBo() {

    }

    public OrderChartTodaySumBo(OrderChartSumDto dto) {

        super(dto);
    }
}
