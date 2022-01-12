package com.lframework.xingyun.chart.vo;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.chart.enums.OrderChartBizType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class QueryOrderChartVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务类型
     */
    @NotNull(message = "业务类型不能为空！")
    @IsEnum(message = "业务类型不存在！", enumClass = OrderChartBizType.class)
    private Integer bizType;
}
