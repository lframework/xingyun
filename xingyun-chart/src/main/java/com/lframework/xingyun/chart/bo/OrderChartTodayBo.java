package com.lframework.xingyun.chart.bo;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.xingyun.chart.dto.OrderChartTodayDto;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderChartTodayBo extends BaseBo<OrderChartTodayDto> {

    /**
     * 单据总金额
     */
    @ApiModelProperty("单据总金额")
    private BigDecimal totalAmount;

    /**
     * 单据总数量
     */
    @ApiModelProperty("单据总数量")
    private Integer totalNum;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createHour;

    public OrderChartTodayBo() {

    }

    public OrderChartTodayBo(OrderChartTodayDto dto) {

        super(dto);
    }
}
