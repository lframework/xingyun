package com.lframework.xingyun.api.controller.chart;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.chart.OrderChartSameMonthBo;
import com.lframework.xingyun.api.bo.chart.OrderChartSumBo;
import com.lframework.xingyun.api.bo.chart.OrderChartTodayBo;
import com.lframework.xingyun.chart.dto.OrderChartSameMonthDto;
import com.lframework.xingyun.chart.dto.OrderChartSumDto;
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

        OrderChartBizType[] bizTypes = OrderChartBizType.values();

        //当日汇总数据
        Map<Integer, OrderChartSumBo> todayMap = new HashMap<>(bizTypes.length, 1);
        //当月汇总数据
        Map<Integer, OrderChartSumBo> sameMonthMap = new HashMap<>(bizTypes.length, 1);

        for (OrderChartBizType bizType : bizTypes) {
            GetOrderChartVo vo = new GetOrderChartVo();
            vo.setBizType(bizType.getCode());

            OrderChartSumDto todaySum = orderChartService.getTodayChartSum(vo);

            OrderChartSumDto sameMonthSum = orderChartService.getSameMonthChartSum(vo);

            todayMap.put(bizType.getCode(), new OrderChartSumBo(todaySum));

            sameMonthMap.put(bizType.getCode(), new OrderChartSumBo(sameMonthSum));
        }

        Map<String, Map<Integer, OrderChartSumBo>> result = new HashMap<>(2, 1);
        result.put("today", todayMap);
        result.put("sameMonth", sameMonthMap);

        return InvokeResultBuilder.success(result);
    }

    @PreAuthorize("@permission.valid(T(com.lframework.common.constants.StringPool).PERMISSION_ADMIN_NAME)")
    @GetMapping("/order/datas/today")
    public InvokeResult orderChartTodayDatas(@Valid QueryOrderChartVo vo) {

        List<OrderChartTodayDto> datas = orderChartService.queryTodayChart(vo);
        List<OrderChartTodayBo> results = Collections.EMPTY_LIST;
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(OrderChartTodayBo::new).collect(Collectors.toList());

            List<OrderChartTodayBo> newResults = new ArrayList<>();

        }

        return InvokeResultBuilder.success(results);
    }

    @PreAuthorize("@permission.valid(T(com.lframework.common.constants.StringPool).PERMISSION_ADMIN_NAME)")
    @GetMapping("/order/datas/samemonth")
    public InvokeResult orderChartSameMonthDatas(@Valid QueryOrderChartVo vo) {

        List<OrderChartSameMonthDto> datas = orderChartService.querySameMonthChart(vo);
        List<OrderChartSameMonthBo> results = Collections.EMPTY_LIST;
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(OrderChartSameMonthBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(results);
    }
}
