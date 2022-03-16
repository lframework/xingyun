package com.lframework.xingyun.chart.vo;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.chart.enums.OrderChartBizType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class QueryOrderChartVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务类型
     */
    @NotEmpty(message = "业务类型不能为空！")
    private List<Integer> bizTypes;
}
