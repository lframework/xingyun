package com.lframework.xingyun.chart.biz.impl;

import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.DateUtil;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.chart.biz.mappers.OrderChartMapper;
import com.lframework.xingyun.chart.biz.service.IOrderChartService;
import com.lframework.xingyun.chart.facade.dto.OrderChartSameMonthDto;
import com.lframework.xingyun.chart.facade.dto.OrderChartSumDto;
import com.lframework.xingyun.chart.facade.dto.OrderChartTodayDto;
import com.lframework.xingyun.chart.facade.entity.OrderChart;
import com.lframework.xingyun.chart.facade.enums.OrderChartBizType;
import com.lframework.xingyun.chart.facade.vo.CreateOrderChartVo;
import com.lframework.xingyun.chart.facade.vo.GetOrderChartVo;
import com.lframework.xingyun.chart.facade.vo.QueryOrderChartVo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderChartServiceImpl extends BaseMpServiceImpl<OrderChartMapper, OrderChart>
    implements IOrderChartService {

  @Transactional
  @Override
  public String create(CreateOrderChartVo vo) {

    if (vo.getCreateTime() == null) {
      vo.setCreateTime(LocalDateTime.now());
    }

    OrderChart record = new OrderChart();
    record.setId(IdUtil.getId());
    record.setTotalAmount(vo.getTotalAmount());
    record.setCreateTime(vo.getCreateTime());
    record.setBizType(EnumUtil.getByCode(OrderChartBizType.class, vo.getBizType()));
    record.setCreateDate(DateUtil.formatDate(vo.getCreateTime().toLocalDate()));
    record.setCreateHour(DateUtil.format(vo.getCreateTime(), StringPool.DATE_TIME_HOUR_PATTER));

    getBaseMapper().insert(record);

    return record.getId();
  }

  @Override
  public OrderChartSumDto getTodayChartSum(GetOrderChartVo vo) {

    LocalDate now = LocalDate.now();

    OrderChartSumDto data = getBaseMapper().getChartSum(vo.getBizTypes(),
        DateUtil.toLocalDateTime(now),
        DateUtil.toLocalDateTimeMax(now));
    if (data == null) {
      data = new OrderChartSumDto();
      data.setTotalAmount(BigDecimal.ZERO);
      data.setTotalNum(0L);
    }

    return data;
  }

  @Override
  public OrderChartSumDto getSameMonthChartSum(GetOrderChartVo vo) {

    LocalDate startDate = LocalDate.now().withDayOfMonth(1);
    LocalDate endDate = startDate.plusMonths(1).minusDays(1);

    OrderChartSumDto data = getBaseMapper().getChartSum(vo.getBizTypes(),
        DateUtil.toLocalDateTime(startDate),
        DateUtil.toLocalDateTimeMax(endDate));
    if (data == null) {
      data = new OrderChartSumDto();
      data.setTotalAmount(BigDecimal.ZERO);
      data.setTotalNum(0L);
    }

    return data;
  }

  @Override
  public List<OrderChartTodayDto> queryTodayChart(QueryOrderChartVo vo) {

    LocalDate now = LocalDate.now();

    List<OrderChartTodayDto> results = getBaseMapper().queryToday(vo.getBizTypes(),
        DateUtil.toLocalDateTime(now),
        DateUtil.toLocalDateTimeMax(now));

    int offset = 24;
    List<OrderChartTodayDto> newResults = new ArrayList<>(offset);

    for (int i = 0; i < offset; i++) {
      LocalTime localTime = LocalTime.of(i, 0, 0);
      String filterDateStr = DateUtil.formatDateTime(LocalDateTime.of(now, localTime),
          StringPool.DATE_TIME_HOUR_PATTER);
      OrderChartTodayDto result = results.stream()
          .filter(t -> t.getCreateHour().equals(filterDateStr))
          .findFirst().orElse(null);
      if (result == null) {
        result = new OrderChartTodayDto();
        result.setCreateHour(filterDateStr);
        result.setTotalAmount(BigDecimal.ZERO);
        result.setTotalNum(0);
      }

      newResults.add(result);
    }

    return newResults;
  }

  @Override
  public List<OrderChartSameMonthDto> querySameMonthChart(QueryOrderChartVo vo) {

    LocalDate startDate = LocalDate.now().withDayOfMonth(1);
    LocalDate endDate = startDate.plusMonths(1).minusDays(1);

    List<OrderChartSameMonthDto> results = getBaseMapper().querySameMonth(vo.getBizTypes(),
        DateUtil.toLocalDateTime(startDate), DateUtil.toLocalDateTimeMax(endDate));

    LocalDate lastDate = startDate.plusMonths(1).minusDays(1);
    int offset = lastDate.getDayOfMonth() - startDate.getDayOfMonth() + 1;
    List<OrderChartSameMonthDto> newResults = new ArrayList<>(offset);

    for (int i = 0; i < offset; i++) {
      String filterDateStr = DateUtil.formatDate(startDate.plusDays(i));
      OrderChartSameMonthDto result = results.stream()
          .filter(t -> t.getCreateDate().equals(filterDateStr))
          .findFirst().orElse(null);
      if (result == null) {
        result = new OrderChartSameMonthDto();
        result.setCreateDate(filterDateStr);
        result.setTotalAmount(BigDecimal.ZERO);
        result.setTotalNum(0);
      }

      newResults.add(result);
    }

    return newResults;
  }
}
