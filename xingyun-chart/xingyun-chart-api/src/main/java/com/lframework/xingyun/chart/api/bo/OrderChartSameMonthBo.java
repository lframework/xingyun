package com.lframework.xingyun.chart.api.bo;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.chart.facade.dto.OrderChartSameMonthDto;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderChartSameMonthBo extends BaseBo<OrderChartSameMonthDto> {

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
    private String createDate;

    public OrderChartSameMonthBo() {

    }

    public OrderChartSameMonthBo(OrderChartSameMonthDto dto) {

        super(dto);
    }
}