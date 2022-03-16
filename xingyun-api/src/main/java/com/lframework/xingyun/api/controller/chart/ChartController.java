package com.lframework.xingyun.api.controller.chart;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.chart.OrderChartSameMonthBo;
import com.lframework.xingyun.api.bo.chart.OrderChartSameMonthSumBo;
import com.lframework.xingyun.api.bo.chart.OrderChartTodaySumBo;
import com.lframework.xingyun.api.bo.chart.OrderChartTodayBo;
import com.lframework.xingyun.chart.dto.OrderChartSameMonthDto;
import com.lframework.xingyun.chart.dto.OrderChartTodayDto;
import com.lframework.xingyun.chart.enums.OrderChartBizType;
import com.lframework.xingyun.chart.service.IOrderChartService;
import com.lframework.xingyun.chart.vo.GetOrderChartVo;
import com.lframework.xingyun.chart.vo.QueryOrderChartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 报表
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/chart")
public class ChartController extends DefaultBaseController {

    @Autowired
    private IOrderChartService orderChartService;

    @PreAuthorize("@permission.valid(T(com.lframework.common.constants.StringPool).PERMISSION_ADMIN_NAME)")
    @GetMapping("/order")
    public InvokeResult orderChart() {

        //当日数据
        Map<String, OrderChartTodaySumBo> todayMap = new HashMap<>(2, 1);
        //当月数据
        Map<String, OrderChartSameMonthSumBo> sameMonthMap = new HashMap<>(2, 1);

        List<Integer> orderBizTypes = CollectionUtil.toList(OrderChartBizType.PURCHASE_ORDER.getCode(), OrderChartBizType.SALE_ORDER.getCode(), OrderChartBizType.RETAIL_OUT_SHEET.getCode());
        todayMap.put("order", getTodayChart(orderBizTypes));
        sameMonthMap.put("order", getSameMonthChart(orderBizTypes));

        List<Integer> returnBizTypes = CollectionUtil.toList(OrderChartBizType.PURCHASE_RETURN.getCode(), OrderChartBizType.SALE_RETURN.getCode(), OrderChartBizType.RETAIL_RETURN.getCode());
        todayMap.put("returned", getTodayChart(returnBizTypes));
        sameMonthMap.put("returned", getSameMonthChart(returnBizTypes));

        Map<String, Map<String, ? extends BaseBo>> result = new HashMap<>(2, 1);
        result.put("today", todayMap);
        result.put("sameMonth", sameMonthMap);

        return InvokeResultBuilder.success(result);
    }

    private OrderChartTodaySumBo getTodayChart(List<Integer> bizTypes) {

        GetOrderChartVo orderSumVo = new GetOrderChartVo();
        orderSumVo.setBizTypes(bizTypes);
        // 今日订单汇总数据
        OrderChartTodaySumBo orderSumBo = new OrderChartTodaySumBo(orderChartService.getTodayChartSum(orderSumVo));
        // 今日订单图表数据
        QueryOrderChartVo chartVo = new QueryOrderChartVo();
        chartVo.setBizTypes(bizTypes);
        List<OrderChartTodayDto> charts = orderChartService.queryTodayChart(chartVo);
        List<OrderChartTodayBo> chartResults = Collections.EMPTY_LIST;
        if (!CollectionUtil.isEmpty(charts)) {
            chartResults = charts.stream().map(OrderChartTodayBo::new).collect(Collectors.toList());
        }
        orderSumBo.setCharts(chartResults);

        return orderSumBo;
    }

    private OrderChartSameMonthSumBo getSameMonthChart(List<Integer> bizTypes) {
        GetOrderChartVo orderSumVo = new GetOrderChartVo();
        orderSumVo.setBizTypes(bizTypes);
        // 当月订单汇总数据
        OrderChartSameMonthSumBo sameMonthOrderSumBo = new OrderChartSameMonthSumBo(orderChartService.getSameMonthChartSum(orderSumVo));
        // 当月订单图表数据
        QueryOrderChartVo chartVo = new QueryOrderChartVo();
        chartVo.setBizTypes(bizTypes);
        List<OrderChartSameMonthDto> sameMonthCharts = orderChartService.querySameMonthChart(chartVo);
        List<OrderChartSameMonthBo> sameMonthChartResults = Collections.EMPTY_LIST;
        if (!CollectionUtil.isEmpty(sameMonthCharts)) {
            sameMonthChartResults = sameMonthCharts.stream().map(OrderChartSameMonthBo::new).collect(Collectors.toList());
        }
        sameMonthOrderSumBo.setCharts(sameMonthChartResults);

        return sameMonthOrderSumBo;
    }
}
