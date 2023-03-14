package com.lframework.xingyun.chart.service;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.chart.dto.OrderChartSameMonthDto;
import com.lframework.xingyun.chart.dto.OrderChartSumDto;
import com.lframework.xingyun.chart.dto.OrderChartTodayDto;
import com.lframework.xingyun.chart.entity.OrderChart;
import com.lframework.xingyun.chart.vo.CreateOrderChartVo;
import com.lframework.xingyun.chart.vo.GetOrderChartVo;
import com.lframework.xingyun.chart.vo.QueryOrderChartVo;
import java.util.List;

public interface OrderChartService extends BaseMpService<OrderChart> {

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateOrderChartVo vo);

  /**
   * 查询当日汇总数据
   *
   * @param vo
   * @return
   */
  OrderChartSumDto getTodayChartSum(GetOrderChartVo vo);

  /**
   * 查询当月汇总数据
   *
   * @param vo
   * @return
   */
  OrderChartSumDto getSameMonthChartSum(GetOrderChartVo vo);

  /**
   * 查询当日数据
   *
   * @param vo
   * @return
   */
  List<OrderChartTodayDto> queryTodayChart(QueryOrderChartVo vo);

  /**
   * 查询当月数据
   *
   * @param vo
   * @return
   */
  List<OrderChartSameMonthDto> querySameMonthChart(QueryOrderChartVo vo);
}
